package com.unknown.numee.child.tasks

import com.unknown.numee.R
import com.unknown.numee.business.beans.*
import com.unknown.numee.util.event.Event
import com.unknown.numee.util.event.EventCallback
import com.unknown.numee.util.event.EventManager
import com.unknown.numee.util.event.events.TaskFinishedEvent
import com.unknown.numee.util.mvp.Presenter
import java.lang.Exception
import java.util.*


class TasksPresenter(
        val model: ModelContract.Model,
        val taskItemListCreator: TaskItemListCreator
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
        model.getUser(model.currentUserID)
        model.requestSchedules(model.currentUserID)
        EventManager.register(TaskFinishedEvent::class.java, taskFinishedCallback)
    }

    override fun onViewDestroy() {
        EventManager.unregister(TaskFinishedEvent::class.java, taskFinishedCallback)
    }

    override fun onItemClicked(item: ViewContract.Item) {
        if (item is TaskItem) {
            if (item.statusOrdinal == Status.CURRENT.ordinal) {
                view.startSubTasksActivity(item.taskID)
            }
        }
    }

    override fun onGoodNightClicked() {
        view.finish()
    }

    override fun onError(e: Exception?) {

    }

    override fun onReceivedGetUserSuccess(user: User?) {
        user?.let {
            model.totalNumCount = it.child?.totalNumCount ?: 0
            view.setWelcomeText(String.format(model.getStringById(R.string.welcome_child), it.child?.name.orEmpty()))
        }
    }

    override fun onReceivedScheduleSuccess(schedule: List<Schedule>?) {
        if (schedule != null && schedule.isNotEmpty()) {
            model.schedule = schedule[0] // find the right schedule for current day

            if (hasDayPassed(model.schedule?.date ?: 0)) {
                val taskIDs = model.schedule?.tasks ?: ""
                val scheduleID = model.schedule?.id ?: ""
                model.requestResetTasks(model.currentUserID, taskIDs, scheduleID)
            } else {
                model.requestTasks(model.currentUserID)
            }
        }
    }

    override fun onReceivedTasksSuccess(tasks: List<Task>?) {
        tasks?.let {
            model.tasks = it
            update()
        }
    }

    override fun onReceivedResetTasksSuccess() {
        model.requestTasks(model.currentUserID)
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
        return ((doneCount.toFloat() / item.size) * 100).toInt()
    }

    private fun hasDayPassed(fromDate: Long): Boolean {
        val scheduleDate = fromDate / 1000
        val today = Date().time / 1000

        return scheduleDate - today > 24 * 60 * 60
    }

    private fun finishTask(taskID: String) {
        val tasks = model.tasks ?: return

        model.requestUpdateTaskStatus(
                model.currentUserID,
                taskID,
                Status.DONE
        )

        val index = tasks.indexOfFirst { it.id == taskID }
        // update subtasks of newly finished task to TO_DO for future
        if (tasks[index].subTasks.isNotEmpty())  {
            model.requestUpdateSubTaskStatus(
                    model.currentUserID,
                    taskID,
                    tasks[index].subTasks[0].id,
                    Status.CURRENT
            )

            for (i in 1 until tasks[index].subTasks.size) {
                model.requestUpdateSubTaskStatus(
                        model.currentUserID,
                        taskID,
                        tasks[index].subTasks[i].id,
                        Status.TO_DO
                )
            }
        }

        if (index != -1 && index + 1 < tasks.size) {
            model.requestUpdateTaskStatus(
                    model.currentUserID,
                    tasks[index + 1].id,
                    Status.CURRENT
            )

            model.requestUpdateSubTaskStatus(
                    model.currentUserID,
                    tasks[index + 1].id,
                    tasks[index + 1].subTasks[0].id,
                    Status.CURRENT
            )
        }

        model.requestTasks(model.currentUserID)
    }
}