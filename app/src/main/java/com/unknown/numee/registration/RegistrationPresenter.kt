package com.unknown.numee.registration

import com.unknown.numee.util.mvp.Presenter


class RegistrationPresenter(
        val model: ModelContract.Model
) : Presenter<ViewContract.View>(), ViewContract.Listener, ModelContract.Listener {

    override fun onCreate() {
        view.setDiagnoses(model.getDiagnoseItems())
    }

    override fun onDiagnoseSelected(position: Int) {

    }

    override fun onSaveClicked() {
        // save user info
        view.startMainActivity()
    }
}