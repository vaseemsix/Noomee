package com.unknown.numee.login

import com.google.firebase.auth.FirebaseUser


interface ViewContract {
    interface View {
        fun startSignInActivity()
        fun startRegistrationActivity()
        fun startMainActivity()
        fun showError(message: String)
    }

    interface Listener {
        fun onCreate()
        fun onSignInWithEmailClicked()
        fun onSignInSuccess(user: FirebaseUser)
        fun onSignInError(message: String)
    }
}