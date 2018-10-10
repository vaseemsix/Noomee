package com.unknown.numee.splashscreen

import com.unknown.numee.business.beans.User
import com.unknown.numee.util.mvp.GeneralModelContract

interface ModelContract {
    interface Model : GeneralModelContract.GeneralModel {
        val currentUserID: String
        val userType: String
        val language: String

        fun requestUser(userID: String)
    }

    interface Listener : GeneralModelContract.GeneralListener {
        fun onError(e: Exception?)
        fun onReceivedGetUserSuccess(user: User?)
    }
}