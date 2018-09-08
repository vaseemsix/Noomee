package com.unknown.numee.child.tasks

import android.content.Context
import com.unknown.numee.business.beans.Schedule
import com.unknown.numee.business.beans.Status
import com.unknown.numee.business.beans.Task


class TaskItemListCreator(val context: Context) {

    fun createItemList(schedule: Schedule, tasks: List<Task>): List<ViewContract.Item> {
        val itemList = mutableListOf<TaskItem>()

        val taskIDs = schedule.tasks
        val tasksInSchedule = tasks.filter { taskIDs.contains(it.id) }

        val lastDoneTaskIndex = tasksInSchedule.indexOfLast { it.status == Status.DONE }
        val currentTaskIndex = tasksInSchedule.indexOfFirst { it.status == Status.CURRENT }

        if (lastDoneTaskIndex != -1) {
            itemList.add(createTaskItem(tasksInSchedule[lastDoneTaskIndex]))
        }
        if (currentTaskIndex != -1) {
            itemList.add(createTaskItem(tasksInSchedule[currentTaskIndex]))
        }

        tasksInSchedule.forEach {
            if (it.status == Status.TO_DO) {
                itemList.add(createTaskItem(it))
            }
        }

        return itemList
    }


    private fun createTaskItem(task: Task): TaskItem {
        return TaskItem(
                time = task.time,
                name = task.name,
                numCount = "${task.numCount} x ",
                taskID = task.id,
                statusOrdinal = task.status.ordinal
        )
    }

}