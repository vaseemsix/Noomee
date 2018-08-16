package com.unknown.numee.child.subtasks

import com.unknown.numee.business.beans.Task
import java.lang.Exception


interface ModelContract {
    interface Model {
        var task: Task?
        var itemList: List<ViewContract.Item>

        fun requestTaskByID(ID: String)
    }

    interface Listener {
        fun onError(e: Exception)
        fun onReceivedGetTaskByIDSuccess(task: Task?)
    }
}