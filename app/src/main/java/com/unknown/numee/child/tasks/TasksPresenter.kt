package com.unknown.numee.child.tasks

import com.unknown.numee.R
import com.unknown.numee.business.beans.*
import com.unknown.numee.util.event.Event
import com.unknown.numee.util.event.EventCallback
import com.unknown.numee.util.event.EventManager
import com.unknown.numee.util.event.events.TaskFinishedEvent
import com.unknown.numee.util.mvp.Presenter
import java.lang.Exception


class TasksPresenter(
        val model: ModelContract.Model,
        val taskItemListCreator: TaskItemListCreator
) : Presenter<ViewContract.View>(), ViewContract.Listener, ModelContract.Listener {

    private val taskFinishedCallback = object : EventCallback {
        override fun onReceived(event: Event) {
            if (event is TaskFinishedEvent) {
                finishTask(event.taskID)
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

    override fun onError(e: Exception?) {

    }

    override fun onReceivedGetUserSuccess(user: User?) {
        user?.let {
            view.setWelcomeText(String.format(model.getStringById(R.string.welcome_child), it.child?.name.orEmpty()))
        }
    }

    override fun onReceivedScheduleSuccess(schedule: List<Schedule>?) {
        if (schedule != null && schedule.isNotEmpty()) {
            model.schedule = schedule[0] // find the right scedule for current day
            model.requestTasks(model.currentUserID)
        }
    }

    override fun onReceivedTasksSuccess(tasks: List<Task>?) {
        tasks?.let {
            model.tasks = it
            update()
        }
    }

    override fun onReceivedUpdateTaskStatusSuccess() {

    }

    override fun onReceivedUpdateSubTaskStatusSuccess() {

    }

    private fun update() {
        val schedule = model.schedule ?: return
        val tasks = model.tasks ?: return

        val itemList = taskItemListCreator.createItemList(schedule, tasks)
        view.setItemList(itemList)
        view.setTasksProgress(getTasksProgress(tasks))
    }

    private fun getTasksProgress(item: List<Task>): Int {
        val doneCount = item.count { it.status == Status.DONE }
        return ((doneCount.toFloat() / item.size) * 100).toInt()
    }

    private fun finishTask(taskID: String) {
        val tasks = model.tasks ?: return

        model.requestUpdateTaskStatus(
                model.currentUserID,
                taskID,
                Status.DONE
        )

        val index = tasks.indexOfFirst { it.id == taskID }
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