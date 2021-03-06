package com.unknown.numee.login

import android.content.Context
import com.google.firebase.auth.FirebaseUser
import com.unknown.numee.business.beans.User
import com.unknown.numee.business.command.GetUser
import com.unknown.numee.business.command.SaveUser
import com.unknown.numee.business.executor.BusinessCommandCallback
import com.unknown.numee.child.push.ChildPushNotificationFirebaseApi
import com.unknown.numee.util.Preferences
import com.unknown.numee.util.mvp.GeneralModel


class LoginModel(context: Context) : GeneralModel(context), ModelContract.Model {

    lateinit var presenter: ModelContract.Listener

    private val childPushNotificationFirebaseApi = ChildPushNotificationFirebaseApi()

    override var firebaseUser: FirebaseUser? = null
    override val userToken: String
        get() = Preferences.userToken

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

    override fun saveUserToken(ID: String, token: String) {
        childPushNotificationFirebaseApi.setUserToken(
                ID,
                token
        )
    }

    override fun getStringById(resId: Int): String {
        return context.resources.getString(resId)
    }

    override fun validatePassword(password: String): Boolean {
        return password.matches(Regex("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#\$%!\\-_?&])(?=\\S+\$).{8,}"))
    }
}