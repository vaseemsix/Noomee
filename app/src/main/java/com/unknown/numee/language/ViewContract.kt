package com.unknown.numee.language

interface ViewContract {

    interface View {
        fun setLanguages(items: Array<String>)
        fun startLoginActivity()
        fun setupLanguageSelectListener()
        fun onSaveClicked(view: android.view.View)
        fun showMessage(message: String)
    }

    interface Listener {
        fun onCreate()
        fun checkIfLanguageAvailable(): Boolean
        fun onLanguageSelected(position: Int)
    }

}