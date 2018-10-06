package com.unknown.numee.parent.schedules

import com.unknown.numee.business.beans.Schedule

interface ViewContract {
    interface View {
        fun setupSchedulesListView()
        fun initViews()
        fun showMessage(message: String)
        fun updateSchedules(schedule: List<Schedule>)
    }

    interface Listener {
        fun onCreate()
        fun onScheduleItemClicked(item: Schedule)
    }
}