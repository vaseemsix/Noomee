package com.unknown.numee.parent.template

import java.lang.Exception


interface ModelContract {
    interface Model {
        fun requestTemplatesName()
    }

    interface Listener {
        fun onReceivedScheduleSuccess(templateNames: List<String>?)
        fun onError(e: Exception?)
    }
}