package com.unknown.numee.parent.subtasks

import android.content.Context
import com.unknown.numee.util.mvp.GeneralModel


class SubTasksModel(context: Context) : GeneralModel(context), ModelContract.Model {

    lateinit var presenter: ModelContract.Listener
    private val taskApiFirebase = SubTasksFirebaseApi()

    override fun requestTaskByID(userID: String, scheduleID: String, taskID: String) {
        taskApiFirebase.getTaskByID(
                    userID,
                    scheduleID,
                    taskID,
                    { task -> presenter.onReceivedGetTaskByIDSuccess(task) },
                    { exception -> presenter.onError(exception) }
        )
    }
}