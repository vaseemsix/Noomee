package com.unknown.numee.registration

import com.unknown.numee.util.mvp.Presenter


class RegistrationPresenter(
        val model: ModelContract.Model
) : Presenter<ViewContract.View>(), ViewContract.Listener, ModelContract.Listener {

    override fun onCreate() {
        view.setRelations(model.getRelationItems())
        view.setDiagnoses(model.getDiagnoseItems())
        view.setSpeak(model.getSpeakItems())
        view.setIQLevels(model.getIQLevelItems())
    }

    override fun onRelationSelected(position: Int) {

    }

    override fun onDiagnoseSelected(position: Int) {

    }

    override fun onSpeakSelected(position: Int) {

    }

    override fun onIQLevelSelected(position: Int) {

    }

    override fun onSaveClicked() {
        // save user info
        view.startMainActivity()
    }
}