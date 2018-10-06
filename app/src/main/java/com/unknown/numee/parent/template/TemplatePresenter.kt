package com.unknown.numee.parent.template

import com.unknown.numee.util.mvp.Presenter
import java.lang.Exception


class TemplatePresenter(
        val model: ModelContract.Model
) : Presenter<ViewContract.View>(), ViewContract.Listener, ModelContract.Listener {

    override fun onCreate() {
        view.initViews()
        model.requestTemplatesName()
    }

    override fun onReceivedScheduleSuccess(templateNames: List<String>?) {
        if (templateNames != null) {
            view.updateTemplatesList(templateNames)
        }
    }

    override fun onError(e: Exception?) {
        if (e != null) {
            view.showMessage(e.message.toString())
        }
    }

    override fun onTemplateItemClicked(templateName: String) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}