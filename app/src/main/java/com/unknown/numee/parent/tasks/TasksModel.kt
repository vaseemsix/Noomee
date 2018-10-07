package com.unknown.numee.parent.tasks

import android.content.Context
import com.unknown.numee.business.beans.Schedule
import com.unknown.numee.util.mvp.GeneralModel


class TasksModel(context: Context) : GeneralModel(context), ModelContract.Model {

    lateinit var presenter: ModelContract.Listener
    private val schedulesApiFirebase = TasksFirebaseApi()

//    override fun requestSchedules(userID: String) {
//        schedulesApiFirebase.getSchedules(
//                userID,
//                { result -> presenter.onReceivedScheduleSuccess(result) },
//                { e -> presenter.onError(e) }
//        )
//    }
//
//    override fun removeSchedule(schedule: Schedule) {
//        schedulesApiFirebase.removeSchedule(
//                schedule
//        ) { presenter.onRemoveScheduleSuccess() }
//    }

}