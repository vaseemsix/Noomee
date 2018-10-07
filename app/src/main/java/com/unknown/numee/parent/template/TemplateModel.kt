package com.unknown.numee.parent.template

import android.content.Context
import com.unknown.numee.business.beans.Task
import com.unknown.numee.util.mvp.GeneralModel


class TemplateModel(context: Context) : GeneralModel(context), ModelContract.Model {

    lateinit var presenter: ModelContract.Listener
    private val templatesFirebaseApi = TemplatesFirebaseApi()
    private var templateName: String = ""

    override fun getTemplateName(): String {
        return templateName
    }


    override fun requestTemplatesName() {
        templatesFirebaseApi.getTemplateNames(
                { result -> presenter.onReceivedScheduleSuccess(result) },
                { e -> presenter.onError(e) }
        )
    }

    override fun requestTemplate(templateName: String) {

        this.templateName = templateName
        templatesFirebaseApi.getTemplate(
                templateName,
                { result -> presenter.onReceivedTemplateSuccess(result) },
                { e -> presenter.onError(e) }
        )
    }

    override fun saveSchedule(templateName: String, tasksList: List<Task>) {
        templatesFirebaseApi.saveScheduleTask(
                tasksList,
                templateName
        ) { presenter.onSaveScheduleSuccess() }
    }
}