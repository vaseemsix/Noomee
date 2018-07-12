package com.unknown.numee.login

import com.google.firebase.auth.FirebaseUser


interface ViewContract {
    interface View {
        fun hasUser(): Boolean
        fun getEmail(): String
        fun getName(): String
        fun getPassword(): String
        fun showSignInView()
        fun showSignUpView()
        fun startSignUpActivity(email: String, password: String)
        fun startSignInActivity(email: String, password: String)
        fun startRegistrationActivity()
        fun startMainActivity()
        fun showError(message: String)
    }

    interface Listener {
        fun onCreate()
        fun onSignUpWithEmailClicked()
        fun onSignInWithEmailClicked()
        fun onSignUpSuccess(user: FirebaseUser)
        fun onSignInSuccess(user: FirebaseUser)
        fun onSignInError(message: String)
    }
}