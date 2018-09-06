package com.unknown.numee.child.reward

import android.content.Context
import com.unknown.numee.util.Preferences
import com.unknown.numee.util.mvp.GeneralModel


class RewardModel(context: Context) : GeneralModel(context), ModelContract.Model {

    lateinit var presenter: ModelContract.Listener

    private val rewardFirebaseApi = RewardFirebaseApi()

    override val currentUserID: String
        get() = Preferences.userID
    override var totalNumCount: Int = 0

    override fun requestTotalNumCount(userID: String) {
        rewardFirebaseApi.getTotalNumCount(
                userID,
                { result -> presenter.onReceivedTotalNumCount(result) },
                { exception -> presenter.onError(exception) }
        )
    }

    override fun requestUpdateTotalNumCount(userID: String, value: Int) {
        rewardFirebaseApi.setTotalNumCount(
                userID,
                value,
                { presenter.onReceivedUpdateTotalNumCount() },
                { exception -> presenter.onError(exception) }
        )
    }
}