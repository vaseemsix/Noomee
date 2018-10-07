package com.unknown.numee.parent.template


interface ViewContract {
    interface View {
        fun initViews()
        fun showMessage(message: String)
        fun updateTemplatesList(templateNamesList: List<String>)
        fun startMainActivity()
    }

    interface Listener {
        fun onCreate()
        fun onTemplateItemClicked(templateName: String)
    }
}