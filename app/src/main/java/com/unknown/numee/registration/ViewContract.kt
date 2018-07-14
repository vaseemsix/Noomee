package com.unknown.numee.registration


interface ViewContract {
    interface View {
        fun setRelations(items: Array<String>)
        fun setDiagnoses(items: Array<String>)
        fun setSpeak(items: Array<String>)
        fun setIQLevels(items: Array<String>)
        fun startMainActivity()
    }

    interface Listener {
        fun onCreate()
        fun onRelationSelected(position: Int)
        fun onDiagnoseSelected(position: Int)
        fun onSpeakSelected(position: Int)
        fun onIQLevelSelected(position: Int)
        fun onSaveClicked()
    }
}