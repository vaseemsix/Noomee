package com.unknown.numee.child.subtasks

import android.os.CountDownTimer
import com.unknown.numee.business.beans.Status
import com.unknown.numee.business.beans.Task


interface ModelContract {
    interface Model {
        val currentUserID: String
        var task: Task?
        var timer: CountDownTimer?
        var itemList: List<ViewContract.Item>

        fun requestTaskByID(userID: String, scheduleID: String, taskID: String)
        fun requestUpdateSubTaskStatus(userID: String, taskID: String, scheduleID: String, subTaskIndex: String, newStatus: Status)
    }

    interface Listener {
        fun onError(e: Exception?)
        fun onReceivedGetTaskByIDSuccess(task: Task?)
        fun onReceivedUpdateSubTaskStatusSuccess()
    }
}