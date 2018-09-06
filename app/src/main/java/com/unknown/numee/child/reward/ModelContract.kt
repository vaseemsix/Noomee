package com.unknown.numee.child.reward

import java.lang.Exception


interface ModelContract {
    interface Model {
        val currentUserID: String
        var totalNumCount: Int

        fun requestTotalNumCount(userID: String)
        fun requestUpdateTotalNumCount(userID: String, value: Int)
    }

    interface Listener {
        fun onError(e: Exception?)
        fun onReceivedTotalNumCount(result: Int?)
        fun onReceivedUpdateTotalNumCount()
    }
}