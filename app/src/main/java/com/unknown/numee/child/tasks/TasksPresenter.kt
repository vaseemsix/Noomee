package com.unknown.numee.child.tasks

import com.unknown.numee.R
import com.unknown.numee.business.beans.*
import com.unknown.numee.business.beans.Calendar
import com.unknown.numee.util.event.Event
import com.unknown.numee.util.event.EventCallback
import com.unknown.numee.util.event.EventManager
import com.unknown.numee.util.event.events.TaskFinishedEvent
import com.unknown.numee.util.extensions.dayOfWeek
import com.unknown.numee.util.mvp.Presenter
import java.util.*


class TasksPresenter(
        val model: ModelContract.Model,
        private val taskItemListCreator: TaskItemListCreator
) : Presenter<ViewContract.View>(), ViewContract.Listener, ModelContract.Listener {

    private val taskFinishedCallback = object : EventCallback {
        override fun onReceived(event: Event) {
            if (event is TaskFinishedEvent) {
                finishTask(event.taskID)
                model.totalNumCount++
            }
        }
    }

    override fun onViewCreated() {
        model.requestUser(model.currentUserID)
        model.requestCalendar(model.currentUserID)
        EventManager.register(TaskFinishedEvent::class.java, taskFinishedCallback)
    }

    override fun onViewDestroy() {
        EventManager.unregister(TaskFinishedEvent::class.java, taskFinishedCallback)
    }

    override fun onItemClicked(item: ViewContract.Item) {
        if (item is TaskItem) {
            if (item.statusOrdinal == Status.CURRENT.ordinal) {
                view.startSubTasksActivity(item.taskID, item.scheduleID)
            }
        }
    }

    override fun onGoodNightClicked() {
        view.finish()
    }

    override fun onError(e: Exception?) {
        view.showMessage(e?.message.orEmpty())
    }

    override fun onReceivedGetUserSuccess(user: User?) {
        user?.let {
            model.totalNumCount = it.child?.totalNumCount ?: 0
            view.setWelcomeText(String.format(model.getStringById(R.string.welcome_child), it.child?.name.orEmpty()))
        }
    }

	override fun onReceivedCalendarSuccess(calendar: Calendar?) {
		calendar?.let {
			val dayOfWeek = Date().dayOfWeek()
			val convertedDayOfWeek = if (dayOfWeek - 2 < 0) 6 else dayOfWeek - 2
			val scheduleID = calendar.days[convertedDayOfWeek]
			if (scheduleID.isNotEmpty()) {
				model.requestSchedule(model.currentUserID, scheduleID)
			} else {
				// show error message?
			}
		}
	}

	override fun onReceivedScheduleSuccess(schedule: Schedule?) {
        if (schedule != null) {
            model.schedule = schedule
            val scheduleID = model.schedule?.id ?: ""

            if (hasDayPassed(model.schedule?.date ?: 0)) {
                val taskIDs = model.schedule?.tasks ?: ""
                model.requestResetTasks(model.currentUserID, taskIDs, scheduleID)
            } else {
                model.requestTasks(model.currentUserID, scheduleID)
            }
        }
    }

    override fun onReceivedTasksSuccess(tasks: List<Task>?) {
        tasks?.let { updateTasks(it) }
    }

    private fun updateTasks(tasksList: List<Task>) {
        val newTasksList: MutableList<Task> = mutableListOf()

        for (task in tasksList) {
            if (task.enable == 1) {
                newTasksList.add(task)
            }
        }

        model.tasks = newTasksList.toList()
        update()
    }

    override fun onReceivedResetTasksSuccess() {
        val scheduleID = model.schedule?.id ?: ""
        model.requestTasks(model.currentUserID, scheduleID)
    }

    override fun onReceivedUpdateTaskStatusSuccess() {

    }

    override fun onReceivedUpdateSubTaskStatusSuccess() {

    }

    private fun update() {
        val schedule = model.schedule ?: return
        val tasks = model.tasks ?: return

        val itemList = taskItemListCreator.createItemList(schedule, tasks)
        if (itemList.isEmpty()) {
            view.setContentVisibility(false)
            view.setCollectedNums(model.totalNumCount)
        } else {
            view.setContentVisibility(true)
            view.setItemList(itemList)
        }
        view.setTasksProgress(getTasksProgress(tasks))
    }

    private fun getTasksProgress(item: List<Task>): Int {
        val doneCount = item.count { it.status == Status.DONE }
        val allCount = item.count { it.enable == 1 }
        return ((doneCount.toFloat() / allCount) * 100).toInt()
    }

    private fun hasDayPassed(fromDate: Long): Boolean {
        val scheduleDate = fromDate / 1000
        val today = Date().time / 1000

        return today - scheduleDate > 24 * 60 * 60
    }

    private fun finishTask(taskID: String) {
        val tasks = model.tasks ?: return
        val scheduleID = model.schedule?.id ?: ""

        model.requestUpdateTaskStatus(
                model.currentUserID,
                taskID,
                scheduleID,
                Status.DONE
        )

        val index = tasks.indexOfFirst { it.id == taskID }
        // update subtasks of newly finished task to TO_DO for future
        var subTasks = tasks[index].subTasks
        subTasks = subTasks.sortedBy { it.order }

        val indexOfFirstSubtask = subTasks.indexOfFirst { it.enable == 1 }
        if (subTasks.isNotEmpty() && indexOfFirstSubtask != -1)  {
            model.requestUpdateSubTaskStatus(
                    model.currentUserID,
                    taskID,
                    scheduleID,
                    indexOfFirstSubtask.toString(),
                    Status.CURRENT)

            for (i in 0 until subTasks.size) {
                if (i == indexOfFirstSubtask) continue
                model.requestUpdateSubTaskStatus(
                        model.currentUserID,
                        taskID,
                        scheduleID,
                        i.toString(),
                        Status.TO_DO)
            }
        }

        if (index != -1 && index + 1 < tasks.size) {
            model.requestUpdateTaskStatus(
                    model.currentUserID,
                    tasks[index + 1].id,
                    scheduleID,
                    Status.CURRENT
            )

            model.requestUpdateSubTaskStatus(
                    model.currentUserID,
                    tasks[index + 1].id,
                    scheduleID,
                    "0",
                    Status.CURRENT)
        }

        model.requestTasks(model.currentUserID, scheduleID)
    }
}