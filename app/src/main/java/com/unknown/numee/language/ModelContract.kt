package com.unknown.numee.language

interface ModelContract {
    interface Model  {
        fun getLanguages(): Array<String>
        fun setLanguage(position: Int)
        fun saveLanguage()
        fun updateResource()
    }

    interface Listener {

    }
}