package com.unknown.numee.parent.tasks

import com.unknown.numee.business.beans.Calendar
import com.unknown.numee.business.beans.Task
import java.lang.Exception


interface ModelContract {
    interface Model {
        fun requestScheduledDays(userID: String)
        fun updateScheduleDays(position: Int)
        fun checkForCalendarPresent(userID: String)
        fun getWeekDays(): MutableList<Boolean>
        fun updateCalendar(calendar: Calendar)
        fun saveScheduleDays(userId: String)
        fun removeScheduleFromDays(position: Int)
        fun requestsTasksList(userID: String)
        fun updateTasksList(tasksList: MutableList<Task>)
        fun updateTaskStatus(status: Boolean, position: Int)
        fun saveTasks(userID: String)
        fun getTasksList(): MutableList<Task>
        fun saveScheduleName(scheduleName: String, userId: String)
    }

    interface Listener {
        fun onError(e: Exception?)
        fun onReceivedCalendarSuccess(calendar: Calendar?)
        fun onReceivedTasksSuccess(tasksList: MutableList<Task>?)
        fun onCalendarExist(calendarExist: Boolean?)
        fun onReceivedSaveCalendarSuccess()
        fun onReceivedSaveTaskSuccess()
        fun onAllTasksSaved()
    }
}