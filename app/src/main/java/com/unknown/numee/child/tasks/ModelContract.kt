package com.unknown.numee.child.tasks

import android.support.annotation.StringRes
import com.unknown.numee.business.beans.*


interface ModelContract {
    interface Model {
        val currentUserID: String
        var schedule: Schedule?
        var tasks: List<Task>?
        var totalNumCount: Int
        fun getStringById(@StringRes resId: Int): String

        fun requestUser(ID: String)
	    fun requestCalendar(userID: String)
        fun requestSchedule(userID: String, scheduleID: String)
        fun requestTasks(userID: String, scheduleID: String)
        fun requestResetTasks(userID: String, taskIDs: String, scheduleID: String)
        fun requestUpdateTaskStatus(userID: String, taskID: String, scheduleID: String, newStatus: Status)
        fun requestUpdateSubTaskStatus(userID: String, taskID: String, scheduleID: String, subTaskIndex: String, newStatus: Status)
    }

    interface Listener {
        fun onError(e: Exception?)
        fun onReceivedGetUserSuccess(user: User?)
	    fun onReceivedCalendarSuccess(calendar: Calendar?)
        fun onReceivedScheduleSuccess(schedule: Schedule?)
        fun onReceivedTasksSuccess(tasks: List<Task>?)
        fun onReceivedResetTasksSuccess()
        fun onReceivedUpdateTaskStatusSuccess()
        fun onReceivedUpdateSubTaskStatusSuccess()
    }
}