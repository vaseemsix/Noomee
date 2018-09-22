package com.unknown.numee.main.password

import android.content.Context
import com.unknown.numee.business.beans.User
import com.unknown.numee.business.command.GetUser
import com.unknown.numee.business.executor.BusinessCommandCallback
import com.unknown.numee.util.Preferences
import com.unknown.numee.util.mvp.GeneralModel
import java.lang.Exception


class PasswordModel(context: Context) : GeneralModel(context), ModelContract.Model {

    lateinit var presenter: ModelContract.Listener
    override val currentUserID: String
        get() = Preferences.userID
    override var currentUser: User? = null

    override fun getUser(userID: String) {
        businessCommandExecutor.execute(
                GetUser(userID,
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
}