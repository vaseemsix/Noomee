package com.unknown.numee.registration

import android.content.Context
import com.unknown.numee.R
import com.unknown.numee.util.mvp.GeneralModel


class RegistrationModel(context: Context) : GeneralModel(context), ModelContract.Model {

    lateinit var presenter: ModelContract.Listener

    override fun getRelationItems(): Array<String> {
        return context.resources.getStringArray(R.array.relations)
    }

    override fun getDiagnoseItems(): Array<String> {
        return context.resources.getStringArray(R.array.diagnoses)
    }

    override fun getSpeakItems(): Array<String> {
        return context.resources.getStringArray(R.array.speak)
    }

    override fun getIQLevelItems(): Array<String> {
        return context.resources.getStringArray(R.array.iq_levels)
    }
}