package com.unknown.numee.registration

import com.unknown.numee.business.beans.Child
import com.unknown.numee.business.beans.User
import com.unknown.numee.util.mvp.GeneralModelContract


interface ModelContract {
    interface Model : GeneralModelContract.GeneralModel {
        var user: User?
        var childInfo: Child?
        val currentUserID: String
        fun getRelationItems(): Array<String>
        fun getDiagnoseItems(): Array<String>
        fun getGenderItems(): Array<String>
        fun getSpeakItems(): Array<String>
        fun getIQLevelItems(): Array<String>
        fun getIndependenceLevelItems(): Array<String>
        fun setGender(position: Int)

        fun getUser(ID: String)
        fun saveUser(user: User)
	    fun saveLocalUser(ID: String, name: String, childName: String)
    }

    interface Listener : GeneralModelContract.GeneralListener {
        fun onError(e: Exception)
        fun onReceivedGetUserSuccess(user: User?)
        fun onReceivedSaveUserSuccess()
    }
}