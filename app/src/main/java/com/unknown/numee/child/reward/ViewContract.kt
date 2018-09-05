package com.unknown.numee.child.reward


interface ViewContract {
    interface View {
        fun getNumCount(): Int
        fun setNumCount(value: Int)
        fun setTotalNumCount(value: Int)
        fun finish()
    }

    interface Listener {
        fun onCreate()
        fun onContinueClicked()
    }
}