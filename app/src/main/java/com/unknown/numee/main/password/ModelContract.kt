package com.unknown.numee.main.password

import com.unknown.numee.business.beans.User
import java.lang.Exception


interface ModelContract {
    interface Model {
        val currentUserID: String
        var currentUser: User?

        fun getUser(userID: String)
    }

    interface Listener {
        fun onError(e: Exception?)
        fun onReceivedGetUserSuccess(user: User?)
    }
}