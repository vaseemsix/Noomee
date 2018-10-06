package com.unknown.numee.language

import android.content.Context
import com.unknown.numee.R
import com.unknown.numee.util.Preferences
import java.util.*


class LanguageSelectionModel(val context: Context): ModelContract.Model {

    private var language: String = Preferences.language

    override fun getLanguages(): Array<String> {
        return context.resources.getStringArray(R.array.languages)
    }

    override fun saveLanguage() {
        Preferences.language = this.language
    }

    override fun updateResource() {
        val myLocale = Locale(Preferences.language)
        val res = context.resources
        val conf = res.configuration
        conf.locale = myLocale
        res.updateConfiguration(conf, res.displayMetrics)
    }

    override fun setLanguage(position: Int) {
        this.language = context.resources.getStringArray(R.array.languages_resources)[position]
    }
}