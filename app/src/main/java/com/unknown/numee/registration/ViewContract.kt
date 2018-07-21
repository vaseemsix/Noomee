package com.unknown.numee.registration


interface ViewContract {
    interface View {
        fun setRelations(items: Array<String>)
        fun setDiagnoses(items: Array<String>)
        fun setSpeak(items: Array<String>)
        fun setIQLevels(items: Array<String>)
        fun setIndependenceLevels(items: Array<String>)
        fun startMainActivity()
    }

    interface Listener {
        fun onCreate()
        fun onNameChanged(name: String)
        fun onAgeChanged(age: String)
        fun onRelationSelected(position: Int)
        fun onDiagnoseSelected(position: Int)
        fun onSpeakSelected(position: Int)
        fun onIQLevelSelected(position: Int)
        fun onIndependenceLevelSelected(position: Int)
        fun onSaveClicked()
    }
}