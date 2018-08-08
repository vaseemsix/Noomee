package com.unknown.numee.child.tasks

import com.unknown.numee.business.beans.Schedule
import com.unknown.numee.business.beans.Task
import java.lang.Exception


interface ModelContract {
    interface Model {
        val currentUserID: String

        fun requestSchedule(userID: String)
        fun requestTasks(userID: String)
    }

    interface Listener {
        fun onError(e: Exception)
        fun onReceivedScheduleSuccess(schedule: List<Schedule>?)
        fun onReceivedTasksSuccess(tasks: List<Task>?)
    }
}