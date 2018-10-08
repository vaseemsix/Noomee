package com.unknown.numee.parent.subtasks

import com.unknown.numee.business.beans.Task

interface ModelContract {
    interface Model {
        fun requestTaskByID(userID: String, scheduleID: String, taskID: String)
    }

    interface Listener {
        fun onError(e: Exception?)
        fun onReceivedGetTaskByIDSuccess(task: Task?)
    }
}