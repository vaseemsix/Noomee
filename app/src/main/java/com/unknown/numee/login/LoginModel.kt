package com.unknown.numee.login

import android.content.Context
import com.google.firebase.auth.FirebaseUser
import com.unknown.numee.business.beans.User
import com.unknown.numee.business.command.GetUser
import com.unknown.numee.business.command.SaveUser
import com.unknown.numee.business.executor.BusinessCommandCallback
import com.unknown.numee.util.Preferences
import com.unknown.numee.util.mvp.GeneralModel
import java.lang.Exception


class LoginModel(context: Context) : GeneralModel(context), ModelContract.Model {

    lateinit var presenter: ModelContract.Listener

    override var firebaseUser: FirebaseUser? = null

    override fun saveUserID(ID: String) {
        Preferences.userID = ID
    }

    override fun getUser(ID: String) {
        businessCommandExecutor.execute(
                GetUser(ID,
                        object : BusinessCommandCallback<User> {
                            override fun onSuccess(result: User?) {
                                presenter.onReceivedGetUserSuccess(result)
                            }

                            override fun onError(e: Exception) {
                                presenter.onError(e)
                            }

                        })
        )
    }

    override fun saveUser(user: User) {
        businessCommandExecutor.execute(
             SaveUser(user,
                     object : BusinessCommandCallback<User> {
                         override fun onSuccess(result: User?) {
                             presenter.onReceivedSaveUserSuccess()
                         }

                         override fun onError(e: Exception) {
                             presenter.onError(e)
                         }

                     }))
    }

}