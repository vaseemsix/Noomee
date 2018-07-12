package com.unknown.numee.registration

import com.unknown.numee.util.mvp.GeneralModelContract


interface ModelContract {
    interface Model : GeneralModelContract.GeneralModel {
        fun getDiagnoseItems(): Array<String>
    }

    interface Listener : GeneralModelContract.GeneralListener {

    }
}