package com.unknown.numee.login

import android.support.annotation.StringRes
import com.google.firebase.auth.FirebaseUser
import com.unknown.numee.business.beans.User
import com.unknown.numee.util.mvp.GeneralModelContract


interface ModelContract {
    interface Model : GeneralModelContract.GeneralModel {
        var firebaseUser: FirebaseUser?
        val userToken: String
        fun saveUserID(ID: String)
        fun getStringById(@StringRes resId: Int): String
        fun validatePassword(password: String): Boolean

        // async
        fun getUser(ID: String)
        fun saveUser(user: User)
        fun saveUserToken(ID: String, token: String)
    }

    interface Listener : GeneralModelContract.GeneralListener {
        fun onError(e: Exception?)
        fun onReceivedGetUserSuccess(user: User?)
        fun onReceivedSaveUserSuccess()
    }
}