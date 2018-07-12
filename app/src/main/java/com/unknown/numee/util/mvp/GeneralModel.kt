package com.unknown.numee.util.mvp

import android.content.Context
import com.unknown.numee.business.executor.BusinessCommandExecutor


abstract class GeneralModel(val context: Context) : GeneralModelContract.GeneralModel {

    protected val businessCommandExecutor = BusinessCommandExecutor()

}