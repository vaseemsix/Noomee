package com.unknown.numee.language

import com.unknown.numee.util.Preferences
import com.unknown.numee.util.mvp.Presenter

class LanguageSelectionPresenter(
        val model: ModelContract.Model
): Presenter<ViewContract.View>(), ViewContract.Listener, ModelContract.Listener {

    override fun onCreate() {
        view.setLanguages(model.getLanguages())
        view.setupLanguageSelectListener()
    }

    override fun checkIfLanguageAvailable(): Boolean {
        model.saveLanguage()
        model.updateResource()
        return Preferences.language.isNotEmpty()
    }

    override fun onLanguageSelected(position: Int) {
        model.setLanguage(position)
//        view.showMessage(model.getLanguages()[position])
    }

}