package com.unknown.numee.splashscreen

interface ViewContract {
    interface View {
        fun startMainActivity()
        fun startLoginActivity()
        fun startRegistrationActivity()
        fun startUserSwitcherActivity()
        fun startLanguageSelectorActivity()
        fun setLanguage(language: String)
    }

    interface Listener {
        fun onCreate()
    }
}