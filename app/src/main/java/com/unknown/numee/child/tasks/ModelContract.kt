package com.unknown.numee.child.tasks

import android.support.annotation.StringRes
import com.unknown.numee.business.beans.Schedule
import com.unknown.numee.business.beans.Status
import com.unknown.numee.business.beans.Task
import com.unknown.numee.business.beans.User


interface ModelContract {
    interface Model {
        val currentUserID: String
        var schedule: Schedule?
        var tasks: List<Task>?
        var totalNumCount: Int
        fun getStringById(@StringRes resId: Int): String

        fun getUser(ID: String)
        fun requestSchedules(userID: String)
        fun requestTasks(userID: String, scheduleID: String)
        fun requestResetTasks(userID: String, taskIDs: String, scheduleID: String)
        fun requestUpdateTaskStatus(userID: String, taskID: String, scheduleID: String, newStatus: Status)
        fun requestUpdateSubTaskStatus(userID: String, taskID: String, scheduleID: String, subTaskIndex: String, newStatus: Status)
    }

    interface Listener {
        fun onError(e: Exception?)
        fun onReceivedGetUserSuccess(user: User?)
        fun onReceivedScheduleSuccess(schedule: List<Schedule>?)
        fun onReceivedTasksSuccess(tasks: List<Task>?)
        fun onReceivedResetTasksSuccess()
        fun onReceivedUpdateTaskStatusSuccess()
        fun onReceivedUpdateSubTaskStatusSuccess()
    }
}