package com.unknown.numee.parent.schedules

import android.content.Context
import com.unknown.numee.business.beans.Schedule
import com.unknown.numee.util.UserPreferences
import com.unknown.numee.util.mvp.GeneralModel


class SchedulesModel(context: Context) : GeneralModel(context), ModelContract.Model {

    lateinit var presenter: ModelContract.Listener
    private val schedulesApiFirebase = SchedulesFirebaseApi()

	override val childName: String
		get() = UserPreferences(context).getUser().childName

	override fun requestSchedules(userID: String) {
        schedulesApiFirebase.getSchedules(
                userID,
                { result -> presenter.onReceivedScheduleSuccess(result) },
                { e -> presenter.onError(e) }
        )
    }

    override fun removeSchedule(schedule: Schedule) {
        schedulesApiFirebase.removeSchedule(
                schedule
        ) { presenter.onRemoveScheduleSuccess() }
    }

}