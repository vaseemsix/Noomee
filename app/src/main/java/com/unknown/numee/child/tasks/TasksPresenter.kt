package com.unknown.numee.child.tasks

import com.unknown.numee.business.beans.Schedule
import com.unknown.numee.business.beans.ScheduleItem
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
            model.schedule = schedule[0]
            model.requestTasks(model.currentUserID)
        }
    }

    override fun onReceivedTasksSuccess(tasks: List<Task>?) {
        if (tasks != null) {
            // change, so that it's done via database
            val scheduleItems = mutableListOf<ScheduleItem>()
            tasks.forEach {
                scheduleItems.add(
                        ScheduleItem(
                                time = "18:00",
                                taskID = it.id,
                                task = it
                        )
                )
            }
            model.schedule = Schedule(
                    model.schedule?.id.orEmpty(),
                    model.schedule?.name.orEmpty(),
                    scheduleItems
            )
            model.schedule?.let {
                view.setItemList(createItemList(it))
            }
        }
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