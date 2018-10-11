package com.unknown.numee.parent.subtasks

import android.content.Context
import com.unknown.numee.business.beans.Status
import com.unknown.numee.business.beans.SubTask
import com.unknown.numee.business.beans.Task
import com.unknown.numee.util.mvp.GeneralModel


class SubTasksModel(context: Context) : GeneralModel(context), ModelContract.Model {

    lateinit var presenter: ModelContract.Listener
    private val taskApiFirebase = SubTasksFirebaseApi()

    override fun requestTaskByID(userID: String, scheduleID: String, taskID: String) {
        taskApiFirebase.getTaskByID(
                    userID,
                    scheduleID,
                    taskID,
                    { task -> presenter.onReceivedGetTaskByIDSuccess(task) },
                    { exception -> presenter.onError(exception) }
        )
    }

    private lateinit var task: Task

    override fun updateTask(task: Task) {
        this.task = task
    }

    override fun getTask(): Task {
        return this.task
    }

    override fun updateTaskStatus(status: Boolean, position: Int) {
        if (status) {
            task.subTasks[position].enable = 1
        } else {
            task.subTasks[position].enable = 0
        }
    }

    override fun updateTaskDuration(duration: Int) {
        task.duration = duration
    }

    override fun updateTaskTime(timeInString: String) {
        task.time = timeInString
    }

    override fun updateTaskName(taskName: String) {
        task.name = taskName
    }

    override fun updateSubTaskName(position: Int, newName: String) {
        task.subTasks[position].name = newName
    }

    override fun saveTask(userID: String, scheduleID: String) {
        var notFoundFirstTask = true
        val newSubTaskList: MutableList<SubTask> = mutableListOf()
        for (i in 0 until task.subTasks.size) {
            val newSubTask: SubTask = task.subTasks[i]

            if (newSubTask.enable != 1) {
                if (newSubTask.status == Status.CURRENT) {
                    newSubTask.status = Status.TO_DO
                }
            } else {
                if ((newSubTask.status == Status.TO_DO) && (notFoundFirstTask)) {
                    notFoundFirstTask = false
                    newSubTask.status = Status.CURRENT
                } else {
                    notFoundFirstTask = false
                }
            }

            newSubTaskList.add(newSubTask)
        }

        task.subTasks = newSubTaskList.toList()

        taskApiFirebase.saveTask(
                userID,
                scheduleID,
                task
        ) { presenter.onReceivedSaveTaskSuccess(scheduleID, task.name) }
    }
}