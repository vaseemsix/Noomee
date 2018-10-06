package com.unknown.numee.login

import com.google.firebase.auth.FirebaseUser
import com.unknown.numee.R
import com.unknown.numee.business.beans.User
import com.unknown.numee.util.Preferences
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
        if (model.validatePassword(view.getPassword())) {
            view.setLoadingVisibility(true)
            view.startSignUpActivity(view.getEmail(), view.getPassword())
        } else {
            view.showError(view.getResource().getString(R.string.password_validation_message))
        }
    }

    override fun onSignInWithEmailClicked() {
        view.setLoadingVisibility(true)
        view.startSignInActivity(view.getEmail(), view.getPassword())
    }

    override fun onEmailSentSuccess() {
        view.showError(view.getResource().getString(R.string.email_sent_message))
    }

    override fun onEmailSentFail(exception: Exception?) {
        if (exception != null) {
            if (exception.message != null) {
                view.showError(exception.message.toString())
            }
        }
    }

    override fun onSignUpSuccess(user: FirebaseUser) {
        model.firebaseUser = user
        view.sendEmailVerification()
        model.getUser(user.uid)
    }

    override fun onSignInSuccess(user: FirebaseUser) {
        model.firebaseUser = user
        model.getUser(user.uid)
    }

    override fun onSignInError(message: String) {
        view.setLoadingVisibility(false)
        view.showError(message)
    }

    override fun onError(e: Exception?) {
        view.setLoadingVisibility(false)
        view.showError(e?.message.orEmpty())
    }

    override fun onReceivedGetUserSuccess(user: User?) {
        view.setLoadingVisibility(false)
        if (user != null) {
            if (model.firebaseUser!!.isEmailVerified) {
                model.saveUserID(user.id)
                if (isChildInfoExist()) {
                    if (isUserTypeExist()) {
                        view.startUserSwitcherActivity()
                    } else {
                        view.startMainActivity()
                    }
                } else {
                    view.startRegistrationActivity()
                }
            } else {
                onEmailSentSuccess()
            }
        } else {
            val currentUser = User(
                    model.firebaseUser?.uid.orEmpty(),
                    model.firebaseUser?.email.orEmpty(),
                    view.getName())
            model.saveUserID(currentUser.id)
            model.saveUser(currentUser)
        }
    }

    private fun isUserTypeExist(): Boolean {
        return Preferences.userType.isNotEmpty()
    }

    private fun isChildInfoExist(): Boolean {
        return Preferences.childInfo
    }

    override fun onReceivedSaveUserSuccess() {
        if (model.firebaseUser!!.isEmailVerified) {
            view.startRegistrationActivity()
        }
    }
}