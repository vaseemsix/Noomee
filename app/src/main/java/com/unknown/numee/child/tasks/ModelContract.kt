package com.unknown.numee.child.tasks

import android.support.annotation.StringRes
import com.unknown.numee.business.beans.Schedule
import com.unknown.numee.business.beans.Status
import com.unknown.numee.business.beans.Task
import com.unknown.numee.business.beans.User
import java.lang.Exception


interface ModelContract {
    interface Model {
        val currentUserID: String
        var schedule: Schedule?
        var tasks: List<Task>?
        var totalNumCount: Int
        fun getStringById(@StringRes resId: Int): String

        fun getUser(ID: String)
        fun requestSchedules(userID: String)
        fun requestTasks(userID: String)
        fun requestUpdateTaskStatus(userID: String, taskID: String, newStatus: Status)
        fun requestUpdateSubTaskStatus(userID: String, taskID: String, subTaskID: String, newStatus: Status)
    }

    interface Listener {
        fun onError(e: Exception?)
        fun onReceivedGetUserSuccess(user: User?)
        fun onReceivedScheduleSuccess(schedule: List<Schedule>?)
        fun onReceivedTasksSuccess(tasks: List<Task>?)
        fun onReceivedUpdateTaskStatusSuccess()
        fun onReceivedUpdateSubTaskStatusSuccess()
    }
}