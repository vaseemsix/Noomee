package com.unknown.numee.registration


interface ViewContract {
    interface View {
        fun setRelations(items: Array<String>)
        fun setDiagnoses(items: Array<String>)
        fun startMainActivity()
    }

    interface Listener {
        fun onCreate()
        fun onDiagnoseSelected(position: Int)
        fun onRelationSelected(position: Int)
        fun onSaveClicked()
    }
}