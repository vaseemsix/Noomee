package com.unknown.numee.main.password


interface ViewContract {
    interface View {
        fun authenticate(email: String, password: String)
        fun finish()
        fun showMessage()
        fun startSwitcherActivity()
    }

    interface Listener {
        fun onCreate()
        fun onSubmitClicked(password: String)
        fun onAuthenticateSuccess()
        fun onAuthenticateError()
    }
}