package com.unknown.numee.parent.schedules

import com.unknown.numee.business.beans.Schedule

interface ModelContract {
    interface Model {
	    val childName: String

        fun requestSchedules(userID: String)
        fun removeSchedule(schedule: Schedule)
    }

    interface Listener {
        fun onError(e: Exception?)
        fun onReceivedScheduleSuccess(schedule: List<Schedule>?)
        fun onRemoveScheduleSuccess()
    }
}