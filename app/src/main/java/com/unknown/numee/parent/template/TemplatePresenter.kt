package com.unknown.numee.parent.template

import com.unknown.numee.util.mvp.Presenter


class TemplatePresenter(
        val model: ModelContract.Model
) : Presenter<ViewContract.View>(), ViewContract.Listener, ModelContract.Listener {

    override fun onCreate() {

    }
}