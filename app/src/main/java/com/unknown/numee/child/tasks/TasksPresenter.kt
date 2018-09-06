package com.unknown.numee.child.tasks

import com.unknown.numee.R
import com.unknown.numee.business.beans.*
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
            update()
        }
    }

    private fun update() {
        val schedule = model.schedule ?: return

        view.setItemList(createItemList(schedule))
        view.setTasksProgress(getTaskProgress(schedule.items))
    }

    private fun createItemList(schedule: Schedule): List<ViewContract.Item> {
        val itemList = mutableListOf<TaskItem>()

        val lastDoneTask = schedule.items.findLast { it.task?.status == Status.DONE }
        lastDoneTask?.let {
            itemList.add(createTaskItem(it))
        }
        schedule.items.forEach {
            if (it.task?.status != Status.DONE) {
                itemList.add(createTaskItem(it))
            }
        }

        return itemList
    }

    private fun createTaskItem(item: ScheduleItem): TaskItem {
        return TaskItem(
                time = item.time,
                name = item.task?.name.orEmpty(),
                numCount = "${item.task?.numCount} x ",
                taskID = item.taskID,
                statusOrdinal = item.task?.status?.ordinal ?: Status.DONE.ordinal
        )
    }


    private fun updateScheduleWithTasks(tasks: List<Task>) {
        model.schedule?.items?.forEach {
            val id = it.taskID
            it.task = tasks.find { it.id == id }
        }
    }

    private fun getTaskProgress(item: List<ScheduleItem>): Int {
        val doneCount = item.count { it.task?.status == Status.DONE }
        return ((doneCount.toFloat() / item.size) * 100).toInt()
    }
}