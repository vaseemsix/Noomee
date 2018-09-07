package com.unknown.numee.child.subtasks

import android.os.CountDownTimer
import com.unknown.numee.business.beans.Status
import com.unknown.numee.business.beans.Task
import java.lang.Exception


interface ModelContract {
    interface Model {
        var task: Task?
        var timer: CountDownTimer?
        var itemList: List<ViewContract.Item>

        fun requestTaskByID(ID: String)
        fun requestUpdateSubTaskStatus(taskID: String, subTaskID: String, newStatus: Status)
    }

    interface Listener {
        fun onError(e: Exception?)
        fun onReceivedGetTaskByIDSuccess(task: Task?)
        fun onReceivedUpdateSubTaskStatusSuccess()
    }
}