package com.unknown.numee.parent.schedules

import com.unknown.numee.business.beans.Schedule
import java.lang.Exception

interface ModelContract {
    interface Model {
        fun requestSchedules(userID: String)
    }

    interface Listener {
        fun onError(e: Exception?)
        fun onReceivedScheduleSuccess(schedule: List<Schedule>?)
    }
}