package com.unknown.numee.registration

import android.content.Context
import com.unknown.numee.R
import com.unknown.numee.business.beans.Child
import com.unknown.numee.business.beans.User
import com.unknown.numee.business.command.GetUser
import com.unknown.numee.business.command.SaveUser
import com.unknown.numee.business.executor.BusinessCommandCallback
import com.unknown.numee.util.Preferences
import com.unknown.numee.util.UserPreferences
import com.unknown.numee.util.mvp.GeneralModel


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

    override fun getGenderItems(): Array<String> {
        return context.resources.getStringArray(R.array.genders)
    }

    override fun getSpeakItems(): Array<String> {
        return context.resources.getStringArray(R.array.speak)
    }

    override fun getIQLevelItems(): Array<String> {
        return context.resources.getStringArray(R.array.iq_levels)
    }

    override fun getIndependenceLevelItems(): Array<String> {
        return context.resources.getStringArray(R.array.independence_levels)
    }

    override fun setGender(position: Int) {
        when (position) {
            0 -> Preferences.gender = "boy"
            1 -> Preferences.gender = "girl"
        }
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

	override fun saveLocalUser(ID: String, name: String, childName: String) {
		UserPreferences(context).saveUser(UserPreferences.LocalUser(
				id = ID,
				name = name,
				childName = childName
		))
	}
}