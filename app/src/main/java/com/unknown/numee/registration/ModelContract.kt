package com.unknown.numee.registration

import com.unknown.numee.business.beans.Child
import com.unknown.numee.business.beans.User
import com.unknown.numee.util.mvp.GeneralModelContract
import java.lang.Exception


interface ModelContract {
    interface Model : GeneralModelContract.GeneralModel {
        var user: User?
        var childInfo: Child?
        val currentUserID: String
        fun getRelationItems(): Array<String>
        fun getDiagnoseItems(): Array<String>
        fun getGenderItems(): Array<String>
        fun getGenderDisplayItems(): Array<String>
        fun getSpeakItems(): Array<String>
        fun getIQLevelItems(): Array<String>
        fun getIndependenceLevelItems(): Array<String>

        fun getUser(ID: String)
        fun saveUser(user: User)
    }

    interface Listener : GeneralModelContract.GeneralListener {
        fun onError(e: Exception)
        fun onReceivedGetUserSuccess(user: User?)
        fun onReceivedSaveUserSuccess()
    }
}