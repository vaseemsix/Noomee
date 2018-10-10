package com.unknown.numee.login

import com.google.firebase.auth.FirebaseUser
import com.unknown.numee.R
import com.unknown.numee.business.beans.User
import com.unknown.numee.util.Preferences
import com.unknown.numee.util.mvp.Presenter


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
            view.showMessage(model.getStringById(R.string.password_validation_message))
        }
    }

    override fun onSignInWithEmailClicked() {
        view.setLoadingVisibility(true)
        view.startSignInActivity(view.getEmail(), view.getPassword())
    }

    override fun onEmailSentSuccess() {
        showEmailSentMessage()
    }

    override fun onEmailSentFail(exception: Exception?) {
        view.showMessage(exception?.message.orEmpty())
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
        view.showMessage(message)
    }

    override fun onError(e: Exception?) {
        view.setLoadingVisibility(false)
        view.showMessage(e?.message.orEmpty())
    }

    override fun onReceivedGetUserSuccess(user: User?) {
        view.setLoadingVisibility(false)

        val isEmailVerified = model.firebaseUser?.isEmailVerified ?: false
        if (!isEmailVerified) {
            showEmailSentMessage()
            return
        }
        var userID = ""
        if (user != null) {
            model.saveUserID(user.id)
            if (user.child != null) {
                if (isUserTypeExist()) {
                    view.startMainActivity()
                } else {
                    view.startUserSwitcherActivity()
                }
            } else {
                view.startRegistrationActivity()
            }
            userID = user.id
        } else {
            val currentUser = User(
                    model.firebaseUser?.uid.orEmpty(),
                    model.firebaseUser?.email.orEmpty(),
                    view.getName())
            model.saveUserID(currentUser.id)
            model.saveUser(currentUser)
            userID = currentUser.id
        }

        model.saveUserToken(userID, model.userToken)
    }

    override fun onReceivedSaveUserSuccess() {
        if (model.firebaseUser!!.isEmailVerified) {
            view.startRegistrationActivity()
        }
    }

    private fun isUserTypeExist(): Boolean {
        return Preferences.userType.isNotEmpty()
    }

    private fun showEmailSentMessage() {
        view.showMessage(model.getStringById(R.string.email_sent_message))
    }

}