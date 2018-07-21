package com.unknown.numee.registration

import com.unknown.numee.business.beans.*
import com.unknown.numee.util.mvp.Presenter
import java.lang.Exception


class RegistrationPresenter(
        val model: ModelContract.Model
) : Presenter<ViewContract.View>(), ViewContract.Listener, ModelContract.Listener {

    override fun onCreate() {
        view.setRelations(model.getRelationItems())
        view.setDiagnoses(model.getDiagnoseItems())
        view.setSpeak(model.getSpeakItems())
        view.setIQLevels(model.getIQLevelItems())

        model.childInfo = Child()
        model.getUser(model.currentUserID)
    }

    override fun onNameChanged(name: String) {
        model.childInfo?.name = name
    }

    override fun onAgeChanged(age: String) {
        val ageValue = try {
            age.toInt()
        } catch (e: NumberFormatException) {
            0
        }
        model.childInfo?.age = ageValue
    }

    override fun onRelationSelected(position: Int) {

    }

    override fun onDiagnoseSelected(position: Int) {
        model.childInfo?.diagnose = model.getDiagnoseItems()[position]
    }

    override fun onSpeakSelected(position: Int) {
        model.childInfo?.canSpeak = model.getSpeakItems()[position]
    }

    override fun onIQLevelSelected(position: Int) {
        model.childInfo?.IQLevel = model.getIQLevelItems()[position]
    }

    override fun onSaveClicked() {
        model.user?.let {
            it.child = model.childInfo
            model.saveUser(it)
        }
    }

    override fun onError(e: Exception) {

    }

    override fun onReceivedGetUserSuccess(user: User?) {
        model.user = user
    }

    override fun onReceivedSaveUserSuccess() {
        view.startMainActivity()
    }
}