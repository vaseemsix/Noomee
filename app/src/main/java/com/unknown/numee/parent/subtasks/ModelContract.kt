package com.unknown.numee.parent.subtasks

import com.unknown.numee.business.beans.Task

interface ModelContract {
    interface Model {
        fun requestTaskByID(userID: String, scheduleID: String, taskID: String)
        fun updateTaskStatus(status: Boolean, position: Int)
        fun updateTask(task: Task)
        fun updateTaskDuration(duration: Int)
        fun updateTaskTime(timeInString: String)
        fun updateTaskName(taskName: String)
        fun updateSubTaskName(position: Int, newName: String)
        fun getTask(): Task
        fun saveTask(userID: String, scheduleID: String)
    }

    interface Listener {
        fun onError(e: Exception?)
        fun onReceivedGetTaskByIDSuccess(task: Task?)
        fun onReceivedSaveTaskSuccess()
    }
}