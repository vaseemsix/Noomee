package com.unknown.numee.child.subtasks

import android.content.Context
import android.os.CountDownTimer
import com.unknown.numee.business.beans.Status
import com.unknown.numee.business.beans.Task
import com.unknown.numee.util.Preferences
import com.unknown.numee.util.mvp.GeneralModel


class SubTasksModel(context: Context) : GeneralModel(context), ModelContract.Model {

    lateinit var presenter: ModelContract.Listener

    override val currentUserID: String
        get() = Preferences.userID
    override var scheduleID: String = ""
    override var taskID: String = ""
    override var task: Task? = null
    override var timer: CountDownTimer? = null
    override var itemList: List<ViewContract.Item> = listOf()

    private val subTasksApi = SubTasksFirebaseApi()

    override fun requestTaskByID(userID: String, scheduleID: String, taskID: String) {
        subTasksApi.getTaskByID(
                userID,
                scheduleID,
                taskID,
                { task -> presenter.onReceivedGetTaskByIDSuccess(task) },
                { exception -> presenter.onError(exception) }
        )
    }

    override fun requestUpdateSubTaskStatus(userID: String, taskID: String, scheduleID: String, subTaskIndex: String, newStatus: Status) {
        subTasksApi.updateSubTaskStatus(
                userID,
                taskID,
                scheduleID,
                subTaskIndex,
                newStatus,
                { presenter.onReceivedUpdateSubTaskStatusSuccess() },
                { exception -> presenter.onError(exception) }
        )
    }
}