package com.unknown.numee.child.reward

import java.lang.Exception


interface ModelContract {
    interface Model {
        val currentUserID: String

        fun requestTotalNumCount(userID: String)
    }

    interface Listener {
        fun onError(e: Exception?)
        fun onReceivedTotalNumCount(result: Int?)
    }
}