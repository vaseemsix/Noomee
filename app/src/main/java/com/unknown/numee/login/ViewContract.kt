package com.unknown.numee.login

import android.content.res.Resources
import com.google.firebase.auth.FirebaseUser
import java.lang.Exception


interface ViewContract {
    interface View {
        fun isSignInVisible(): Boolean
        fun hasUser(): Boolean
        fun getEmail(): String
        fun getName(): String
        fun getPassword(): String
        fun getResource(): Resources
        fun showSignInView()
        fun showSignUpView()
        fun setLoadingVisibility(isVisible: Boolean)
        fun startSignUpActivity(email: String, password: String)
        fun startSignInActivity(email: String, password: String)
        fun startUserSwitcherActivity()
        fun sendEmailVerification()
        fun startRegistrationActivity()
        fun startMainActivity()
        fun showError(message: String)
    }

    interface Listener {
        fun onCreate()
        fun onSwitchClicked()
        fun onSignUpWithEmailClicked()
        fun onSignInWithEmailClicked()
        fun onEmailSentSuccess()
        fun onEmailSentFail(exception: Exception?)
        fun onSignUpSuccess(user: FirebaseUser)
        fun onSignInSuccess(user: FirebaseUser)
        fun onSignInError(message: String)
    }
}