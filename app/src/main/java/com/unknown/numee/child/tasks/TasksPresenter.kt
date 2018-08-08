package com.unknown.numee.child.tasks

import com.unknown.numee.business.beans.Schedule
import com.unknown.numee.business.beans.Task
import com.unknown.numee.util.mvp.Presenter
import java.lang.Exception


class TasksPresenter(
        val model: ModelContract.Model
) : Presenter<ViewContract.View>(), ViewContract.Listener, ModelContract.Listener {

    override fun onViewCreated() {
        model.requestSchedule(model.currentUserID)
    }

    override fun onError(e: Exception) {

    }

    override fun onReceivedScheduleSuccess(schedule: List<Schedule>?) {
        if (schedule != null && schedule.isNotEmpty()) {
            view.setItemList(createItemList(schedule[0]))
        }
    }

    override fun onReceivedTasksSuccess(tasks: List<Task>?) {

    }

    private fun createItemList(schedule: Schedule): List<ViewContract.Item> {
        val itemList = mutableListOf<TaskItem>()

        schedule.items.forEach {
            itemList.add(
                    TaskItem(
                            it.time,
                            it.task?.name.orEmpty()
                    )
            )
        }

        return itemList
    }
}