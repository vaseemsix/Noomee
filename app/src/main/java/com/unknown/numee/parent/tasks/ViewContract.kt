package com.unknown.numee.parent.tasks

interface ViewContract {
    interface View {
        fun initViews()
        fun showMessage(message: String)
    }

    interface Listener {
        fun onCreate()
        fun onDayItemClicked(position: Int)
    }
}