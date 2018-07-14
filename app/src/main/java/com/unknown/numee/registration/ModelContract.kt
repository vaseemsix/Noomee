package com.unknown.numee.registration

import com.unknown.numee.util.mvp.GeneralModelContract


interface ModelContract {
    interface Model : GeneralModelContract.GeneralModel {
        fun getRelationItems(): Array<String>
        fun getDiagnoseItems(): Array<String>
        fun getSpeakItems(): Array<String>
        fun getIQLevelItems(): Array<String>
    }

    interface Listener : GeneralModelContract.GeneralListener {

    }
}