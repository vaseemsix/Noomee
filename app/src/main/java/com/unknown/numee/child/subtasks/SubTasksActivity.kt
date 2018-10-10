package com.unknown.numee.child.subtasks

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import android.widget.ProgressBar
import android.widget.TextView
import com.unknown.numee.R
import com.unknown.numee.base.BaseActivity
import com.unknown.numee.child.reward.RewardActivity
import com.unknown.numee.util.widget.SubTasksView


class SubTasksActivity : BaseActivity(), ViewContract.View {

    companion object {

        const val TASK_ID = "TASK_ID"
        const val SCHEDULE_ID = "SCHEDULE_ID"

        fun startActivity(context: Context, taskID: String, scheduleID: String) {
            val intent = Intent(context, SubTasksActivity::class.java)
            intent.putExtra(TASK_ID, taskID)
            intent.putExtra(SCHEDULE_ID, scheduleID)
            context.startActivity(intent)
        }
    }

    private lateinit var presenter: ViewContract.Listener

    private var taskID: String = ""
    private var scheduleID: String = ""

    private lateinit var titleView: TextView
    private lateinit var subTitleView: TextView
    private lateinit var timeView: TextView
    private lateinit var progressView: ProgressBar
    private lateinit var subTasksView: SubTasksView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_subtasks)
        window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)

        initExtras(intent.extras)
        initReferences()
        initPresenter()
        presenter.onCreate()
    }

    override fun getTaskID(): String {
        return taskID
    }

    override fun getScheduleID(): String {
        return scheduleID
    }

    override fun setTitle(title: String) {
        titleView.text = title
    }

    override fun setSubTitle(subTitle: String) {
        subTitleView.text = subTitle
    }

    override fun setSubTasksProgress(progress: Int) {
        progressView.progress = progress
    }

    override fun setSubTasksTime(time: String) {
        timeView.text = time
    }

    override fun setSubTasksTimeVisibility(isVisible: Boolean) {
        timeView.visibility = if (isVisible) View.VISIBLE else View.INVISIBLE
    }

    override fun setItemList(itemList: List<ViewContract.Item>) {
        subTasksView.setItemList(itemList)
    }

    override fun showRewardActivity(numCount: Int) {
        RewardActivity.startActivity(this, numCount)
        finish()
    }

    private fun initExtras(extras: Bundle) {
        taskID = extras.getString(TASK_ID, "")
        scheduleID = extras.getString(SCHEDULE_ID, "")
    }

    private fun initReferences() {
        titleView = findViewById(R.id.activity_subtasks__txt_title)
        subTitleView = findViewById(R.id.activity_subtasks__txt_sub_title)
        timeView = findViewById(R.id.activity_subtasks__txt_time)
        progressView = findViewById(R.id.activity_subtasks__progress)

        subTasksView = findViewById(R.id.activity_subtasks__list_subtasks)
        subTasksView.setOnClickListener { item -> presenter.onItemClicked(item) }
    }

    private fun initPresenter() {
        val model = SubTasksModel(this)
        val subTasksPresenter = SubTasksPresenter(model)
        subTasksPresenter.setView(this)
        model.presenter = subTasksPresenter
        presenter = subTasksPresenter
    }
}