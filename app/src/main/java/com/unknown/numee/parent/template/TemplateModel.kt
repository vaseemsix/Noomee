package com.unknown.numee.parent.template

import android.content.Context
import com.unknown.numee.util.mvp.GeneralModel


class TemplateModel(context: Context) : GeneralModel(context), ModelContract.Model {

    lateinit var presenter: ModelContract.Listener
}