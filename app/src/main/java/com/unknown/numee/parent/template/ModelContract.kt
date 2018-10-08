package com.unknown.numee.parent.template

import com.unknown.numee.business.beans.Task
import java.lang.Exception


interface ModelContract {
    interface Model {
        fun requestTemplatesName()
        fun requestTemplate(templateName: String)
        fun getTemplateId(): String
        fun saveSchedule(templateName: String, tasksList: List<Task>)
        fun saveTemplateName(templateName: String)
        fun getTemplateName(): String
    }

    interface Listener {
        fun onReceivedScheduleSuccess(templateNames: List<String>?)
        fun onReceivedTemplateSuccess(template: List<Task>?)
        fun onSaveScheduleSuccess()
        fun onError(e: Exception?)
    }
}