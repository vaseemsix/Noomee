package com.unknown.numee.child.tasks

import com.unknown.numee.R
import com.unknown.numee.business.beans.Schedule
import com.unknown.numee.business.beans.Status
import com.unknown.numee.business.beans.Task
import com.unknown.numee.business.beans.User
import com.unknown.numee.util.mvp.Presenter
import java.lang.Exception


class TasksPresenter(
        val model: ModelContract.Model
) : Presenter<ViewContract.View>(), ViewContract.Listener, ModelContract.Listener {

    override fun onViewCreated() {
        model.getUser(model.currentUserID)
        model.requestSchedule(model.currentUserID)
    }

    override fun onItemClicked(item: ViewContract.Item) {
        if (item is TaskItem) {
            view.startSubTasksActivity(item.taskID)
        }
    }

    override fun onError(e: Exception) {

    }

    override fun onReceivedGetUserSuccess(user: User?) {
        user?.let {
            view.setWelcomeText(String.format(model.getStringById(R.string.welcome_child), it.child?.name.orEmpty()))
        }
    }

    override fun onReceivedScheduleSuccess(schedule: List<Schedule>?) {
        if (schedule != null && schedule.isNotEmpty()) {
            model.schedule = schedule[0]
            model.requestTasks(model.currentUserID)
        }
    }

    override fun onReceivedTasksSuccess(tasks: List<Task>?) {
        tasks?.let {
            updateScheduleWithTasks(tasks) // change, so that it's done via database
            model.schedule?.let {
                view.setItemList(createItemList(it))
            }
        }
    }

    private fun createItemList(schedule: Schedule): List<ViewContract.Item> {
        val itemList = mutableListOf<TaskItem>()

        schedule.items.forEach {
            if (it.task?.status != Status.TO_DO) {
                itemList.add(
                        TaskItem(
                                time = it.time,
                                name = it.task?.name.orEmpty(),
                                noomeeCount = "${it.noomeeCount} x ",
                                taskID = it.taskID,
                                isDone = it.task?.status == Status.DONE
                        )
                )
            }
        }

        return itemList
    }

    private fun updateScheduleWithTasks(tasks: List<Task>) {
        model.schedule?.items?.forEach {
            val id = it.taskID
            it.task = tasks.find { it.id == id }
        }
    }
}