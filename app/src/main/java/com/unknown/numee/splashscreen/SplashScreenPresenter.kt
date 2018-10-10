package com.unknown.numee.splashscreen

import com.google.firebase.auth.FirebaseAuth
import com.unknown.numee.business.beans.User
import com.unknown.numee.util.mvp.Presenter

class SplashScreenPresenter(
        val model: ModelContract.Model
) : Presenter<ViewContract.View>(), ViewContract.Listener, ModelContract.Listener {

    override fun onCreate() {
        if (isUserLoggedIn()) {
            if (isUserEmailVerified()) {
                model.requestUser(model.currentUserID)
            } else {
                view.startLoginActivity()
            }
        } else {
            if (!isLanguageSelected()) {
                view.startLanguageSelectorActivity()
            } else {
                view.startLoginActivity()
            }
        }

        if (isLanguageSelected()) {
            view.setLanguage(model.language)
        }
    }

    override fun onError(e: Exception?) {
        view.startLoginActivity()
    }

    override fun onReceivedGetUserSuccess(user: User?) {
        if (user != null) {
            if (user.child != null) {
                if (isUserTypeSet()) {
                    view.startMainActivity()
                } else {
                    view.startUserSwitcherActivity()
                }
            } else {
                view.startRegistrationActivity()
            }
        } else {
            view.startLoginActivity()
        }
    }

    private fun isUserLoggedIn(): Boolean {
        return model.currentUserID.isNotEmpty()
    }

    private fun isUserEmailVerified(): Boolean {
        return FirebaseAuth.getInstance().currentUser?.isEmailVerified ?: false
    }

    private fun isUserTypeSet(): Boolean {
        return model.userType.isNotEmpty()
    }

    private fun isLanguageSelected(): Boolean {
        return model.language.isNotEmpty()
    }

}