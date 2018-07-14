package com.unknown.numee.login

import com.google.firebase.auth.FirebaseUser
import com.unknown.numee.business.beans.User
import com.unknown.numee.util.mvp.Presenter
import java.lang.Exception


class LoginPresenter(
        val model: ModelContract.Model
) : Presenter<ViewContract.View>(), ViewContract.Listener, ModelContract.Listener {

    override fun onCreate() {
        if (view.hasUser()) {
            view.showSignInView()
        } else {
            view.showSignUpView()
        }
    }

    override fun onSwitchClicked() {
        if (view.isSignInVisible()) {
            view.showSignUpView()
        } else {
            view.showSignInView()
        }
    }

    override fun onSignUpWithEmailClicked() {
        view.setLoadingVisibility(true)
        view.startSignUpActivity(view.getEmail(), view.getPassword())
    }

    override fun onSignInWithEmailClicked() {
        view.setLoadingVisibility(true)
        view.startSignInActivity(view.getEmail(), view.getPassword())
    }

    override fun onSignUpSuccess(user: FirebaseUser) {
        model.firebaseUser = user
        model.getUser(user.uid)
    }

    override fun onSignInSuccess(user: FirebaseUser) {
        model.firebaseUser = user
        model.getUser(user.uid)
    }

    override fun onSignInError(message: String) {
        view.showError(message)
    }

    override fun onError(e: Exception) {
        view.showError(e.message.orEmpty())
    }

    override fun onReceivedGetUserSuccess(user: User?) {
        view.setLoadingVisibility(false)
        if (user != null) {
            model.saveUserID(user.id)
            view.startMainActivity()
        } else {
            val currentUser = User(
                    model.firebaseUser?.uid.orEmpty(),
                    model.firebaseUser?.email.orEmpty(),
                    view.getName())
            model.saveUserID(currentUser.id)
            model.saveUser(currentUser)
        }
    }

    override fun onReceivedSaveUserSuccess() {
        view.startRegistrationActivity()
    }
}