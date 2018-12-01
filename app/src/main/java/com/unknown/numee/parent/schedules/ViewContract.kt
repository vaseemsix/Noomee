package com.unknown.numee.parent.schedules

import com.unknown.numee.business.beans.Schedule

interface ViewContract {
    interface View {
        fun setupSchedulesListView()
        fun initViews()
        fun showMessage(message: String)
	    fun setSchedulesTitle(childName: String)
        fun updateSchedules(schedule: List<Schedule>)
        fun openTasksActivity(scheduleId: String, scheduleName: String)
    }

    interface Listener {
        fun onCreate()
        fun onScheduleItemClicked(item: Schedule)
        fun onScheduleItemRemoved(item: Schedule)
    }
}