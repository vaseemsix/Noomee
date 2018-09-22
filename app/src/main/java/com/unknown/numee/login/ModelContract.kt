package com.unknown.numee.login

import com.google.firebase.auth.FirebaseUser
import com.unknown.numee.business.beans.User
import com.unknown.numee.util.mvp.GeneralModelContract
import java.lang.Exception


interface ModelContract {
    interface Model : GeneralModelContract.GeneralModel {
        var firebaseUser: FirebaseUser?
        fun saveUserID(ID: String)
        fun getUser(ID: String)
        fun saveUser(user: User)
    }

    interface Listener : GeneralModelContract.GeneralListener {
        fun onError(e: Exception?)
        fun onReceivedGetUserSuccess(user: User?)
        fun onReceivedSaveUserSuccess()
    }
}