package com.unknown.numee.child.tasks

import com.unknown.numee.business.beans.Task


interface ModelContract {
    interface Model {
        val currentUserID: String

        fun requestTasks(userID: String)
    }

    interface Listener {
        fun onReceivedTasksSuccess(tasks: List<Task>?)
    }
}