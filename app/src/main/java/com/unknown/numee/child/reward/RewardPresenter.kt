package com.unknown.numee.child.reward

import com.unknown.numee.util.mvp.Presenter
import java.lang.Exception


class RewardPresenter(
        val model: ModelContract.Model
) : Presenter<ViewContract.View>(), ViewContract.Listener, ModelContract.Listener {

    override fun onCreate() {
        view.setNumCount(view.getNumCount())

        model.requestTotalNumCount(model.currentUserID)
    }

    override fun onContinueClicked() {
        view.finish()
    }

    override fun onError(e: Exception?) {

    }

    override fun onReceivedTotalNumCount(result: Int?) {
        result?.let {
            view.setTotalNumCount(it)
        }
    }
}