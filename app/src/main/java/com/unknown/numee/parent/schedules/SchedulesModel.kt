package com.unknown.numee.parent.schedules

import android.content.Context
import com.unknown.numee.util.mvp.GeneralModel


class SchedulesModel(context: Context) : GeneralModel(context), ModelContract.Model {

    lateinit var presenter: ModelContract.Listener
    private val tasksApiFirebase = SchedulesFirebaseApi()

    override fun requestSchedules(userID: String) {
        tasksApiFirebase.getSchedules(
                userID,
                { result -> presenter.onReceivedScheduleSuccess(result) },
                { e -> presenter.onError(e) }
        )
    }

}