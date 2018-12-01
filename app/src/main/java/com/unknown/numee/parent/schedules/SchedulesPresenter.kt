package com.unknown.numee.parent.schedules

import com.unknown.numee.business.beans.Schedule
import com.unknown.numee.util.Preferences
import com.unknown.numee.util.mvp.Presenter


class SchedulesPresenter(
        val model: ModelContract.Model
) : Presenter<ViewContract.View>(), ViewContract.Listener, ModelContract.Listener {

    override fun onCreate() {
        view.initViews()
        view.setupSchedulesListView()
	    view.setSchedulesTitle(model.childName)
        model.requestSchedules(Preferences.userID)
    }

    override fun onError(e: Exception?) {
        if (e != null) {
            view.showMessage(e.message.toString())
        }
    }

    override fun onReceivedScheduleSuccess(schedule: List<Schedule>?) {
        if (schedule != null && schedule.isNotEmpty()) {
            view.updateSchedules(schedule)
        } else {
            view.updateSchedules(listOf())
        }
    }

    override fun onScheduleItemClicked(item: Schedule) {
        view.openTasksActivity(item.id, item.name)
    }

    override fun onScheduleItemRemoved(item: Schedule) {
        model.removeSchedule(item)
    }

    override fun onRemoveScheduleSuccess() {
        model.requestSchedules(Preferences.userID)
        view.showMessage("Schedule Removed Successfully")
    }

}