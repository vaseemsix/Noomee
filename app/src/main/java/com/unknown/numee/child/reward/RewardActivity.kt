package com.unknown.numee.child.reward

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.CardView
import android.widget.TextView
import com.unknown.numee.R
import com.unknown.numee.base.BaseActivity

class RewardActivity : BaseActivity(), ViewContract.View {

    companion object {
        private const val EXTRA_NUM_COUNT = "extra_num_count"

        fun startActivity(context: Context, numCount: Int) {
            val intent = Intent(context, RewardActivity::class.java)
            intent.putExtra(EXTRA_NUM_COUNT, numCount)
            context.startActivity(intent)
        }
    }

    private lateinit var presenter: ViewContract.Listener

    private lateinit var numCountView: TextView
    private lateinit var totalNumCountView: TextView
    private lateinit var continueBtnView: CardView

    private var currentNumCount: Int = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_reward)
        initExtras()
        initReferences()

        initPresenter()
        presenter.onCreate()
    }

    override fun getNumCount(): Int {
        return currentNumCount
    }

    override fun setNumCount(value: Int) {
        numCountView.text = "+ $value"
    }

    override fun setTotalNumCount(value: Int) {
        totalNumCountView.text = value.toString()
    }

    private fun initExtras() {
        currentNumCount = intent.getIntExtra(EXTRA_NUM_COUNT, 1)
    }

    private fun initReferences() {
        numCountView = findViewById(R.id.activity_reward__txt_num_count)
        totalNumCountView = findViewById(R.id.activity_reward__txt_total_num_count)
        continueBtnView = findViewById(R.id.activity_reward__btn_continue)

        continueBtnView.setOnClickListener { presenter.onContinueClicked() }
    }

    private fun initPresenter() {
        val rewardModel = RewardModel(this)
        val rewardPresenter = RewardPresenter(rewardModel)
        rewardPresenter.setView(this)
        rewardModel.presenter = rewardPresenter
        presenter = rewardPresenter
    }
}
