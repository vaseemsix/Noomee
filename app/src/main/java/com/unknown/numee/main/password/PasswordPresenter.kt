package com.unknown.numee.main.password

import com.unknown.numee.business.beans.User
import com.unknown.numee.util.event.EventManager
import com.unknown.numee.util.event.events.WrongPasswordEvent
import com.unknown.numee.util.mvp.Presenter


class PasswordPresenter(
        val model: ModelContract.Model
) : Presenter<ViewContract.View>(),
        ViewContract.Listener,
        ModelContract.Listener {

    override fun onCreate() {
        model.getUser(model.currentUserID)
    }

    override fun onSubmitClicked(password: String) {
        view.setPasswordEditEnabled(false)
        if (password.isEmpty()) {
            view.showMessage()
        } else {
            view.authenticate(model.currentUser?.email.orEmpty(), password)
        }
    }

    override fun onAuthenticateSuccess() {
        view.startSwitcherActivity()
        view.finish()
    }

    override fun onAuthenticateError() {
        view.setPasswordEditEnabled(true)
        view.showMessage()
        EventManager.send(WrongPasswordEvent())
    }

    override fun onError(e: Exception?) {
        view.setPasswordEditEnabled(true)
        view.showMessage()
        EventManager.send(WrongPasswordEvent())
    }

    override fun onReceivedGetUserSuccess(user: User?) {
        model.currentUser = user
    }

}