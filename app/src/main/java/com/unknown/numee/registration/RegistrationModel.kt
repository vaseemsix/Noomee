package com.unknown.numee.registration

import android.content.Context
import com.unknown.numee.R
import com.unknown.numee.util.mvp.GeneralModel


class RegistrationModel(context: Context) : GeneralModel(context), ModelContract.Model {

    lateinit var presenter: ModelContract.Listener

    override fun getDiagnoseItems(): Array<String> {
        return context.resources.getStringArray(R.array.diagnoses)
    }
}