package com.unknown.numee.login

import android.support.annotation.StringRes
import com.google.firebase.auth.FirebaseUser
import com.unknown.numee.business.beans.User
import com.unknown.numee.util.mvp.GeneralModelContract


interface ModelContract {
    interface Model : GeneralModelContract.GeneralModel {
        var firebaseUser: FirebaseUser?
        fun saveUserID(ID: String)
        fun getUser(ID: String)
        fun saveUser(user: User)
        fun getStringById(@StringRes resId: Int): String
        fun validatePassword(password: String): Boolean
    }

    interface Listener : GeneralModelContract.GeneralListener {
        fun onError(e: Exception?)
        fun onReceivedGetUserSuccess(user: User?)
        fun onReceivedSaveUserSuccess()
    }
}