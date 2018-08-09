package com.unknown.numee.child.tasks

import android.support.annotation.StringRes
import com.unknown.numee.business.beans.Schedule
import com.unknown.numee.business.beans.Task
import com.unknown.numee.business.beans.User
import java.lang.Exception


interface ModelContract {
    interface Model {
        val currentUserID: String
        var schedule: Schedule?
        fun getStringById(@StringRes resId: Int): String

        fun getUser(ID: String)
        fun requestSchedule(userID: String)
        fun requestTasks(userID: String)
    }

    interface Listener {
        fun onError(e: Exception)
        fun onReceivedGetUserSuccess(user: User?)
        fun onReceivedScheduleSuccess(schedule: List<Schedule>?)
        fun onReceivedTasksSuccess(tasks: List<Task>?)
    }
}