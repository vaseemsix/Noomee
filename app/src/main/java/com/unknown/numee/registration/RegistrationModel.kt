package com.unknown.numee.registration

import android.content.Context
import com.unknown.numee.R
import com.unknown.numee.business.beans.Child
import com.unknown.numee.business.beans.User
import com.unknown.numee.business.command.GetUser
import com.unknown.numee.business.command.SaveUser
import com.unknown.numee.business.executor.BusinessCommandCallback
import com.unknown.numee.util.Preferences
import com.unknown.numee.util.mvp.GeneralModel
import java.lang.Exception


class RegistrationModel(context: Context) : GeneralModel(context), ModelContract.Model {

    lateinit var presenter: ModelContract.Listener

    override var user: User? = null
    override var childInfo: Child? = null
    override val currentUserID: String
        get() = Preferences.userID

    override fun getRelationItems(): Array<String> {
        return context.resources.getStringArray(R.array.relations)
    }

    override fun getDiagnoseItems(): Array<String> {
        return context.resources.getStringArray(R.array.diagnoses)
    }

    override fun getSpeakItems(): Array<String> {
        return context.resources.getStringArray(R.array.speak)
    }

    override fun getIQLevelItems(): Array<String> {
        return context.resources.getStringArray(R.array.iq_levels)
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