package com.unknown.numee.child.story

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.WindowManager
import android.widget.ImageView
import android.widget.TextView
import com.unknown.numee.R
import com.unknown.numee.base.BaseActivity
import com.unknown.numee.child.reward.RewardActivity
import com.unknown.numee.child.subtasks.SubTasksActivity
import com.unknown.numee.child.subtasks.SubTasksModel
import com.unknown.numee.child.subtasks.SubTasksPresenter
import com.unknown.numee.child.subtasks.ViewContract
import com.unknown.numee.util.extensions.loadImage
import com.unknown.numee.util.widget.NoomeeProgressView

class ChildStoryActivity : BaseActivity(), ViewContract.View {

	companion object {

		fun startActivity(context: Context, taskID: String, scheduleID: String) {
			val intent = Intent(context, ChildStoryActivity::class.java)
			intent.putExtra(SubTasksActivity.TASK_ID, taskID)
			intent.putExtra(SubTasksActivity.SCHEDULE_ID, scheduleID)
			context.startActivity(intent)
		}
	}

	private lateinit var presenter: ViewContract.Listener

	private var taskID: String = ""
	private var scheduleID: String = ""

	private lateinit var titleView: TextView
	private lateinit var subTitleView: TextView
	private lateinit var progressView: NoomeeProgressView
	private lateinit var imageView: ImageView

	private var item: ViewContract.Item? = null

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.activity_child_story)
		window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)

		initExtras(intent.extras)
		initReferences()
		initPresenter()
		presenter.onCreate()
	}

	private fun initExtras(extras: Bundle) {
		taskID = extras.getString(SubTasksActivity.TASK_ID, "")
		scheduleID = extras.getString(SubTasksActivity.SCHEDULE_ID, "")
	}

	private fun initReferences() {
		titleView = findViewById(R.id.activity_child_story__txt_title)
		subTitleView = findViewById(R.id.activity_child_story__txt_sub_title)
		progressView = findViewById(R.id.activity_child_story__progress)
		imageView = findViewById(R.id.activity_child_story__img)

		imageView.setOnClickListener { item?.let { itm -> presenter.onItemClicked(itm) } }
	}

	private fun initPresenter() {
		val model = SubTasksModel(this)
		val subTasksPresenter = SubTasksPresenter(model)
		subTasksPresenter.setView(this)
		model.presenter = subTasksPresenter
		presenter = subTasksPresenter
	}


	override fun getTaskID(): String {
		return taskID
	}

	override fun getScheduleID(): String {
		return scheduleID
	}

	override fun setTitle(title: String) {
		subTitleView.text = title
	}

	override fun setSubTitle(subTitle: String) {
		titleView.text = subTitle
	}

	override fun setSubTasksProgress(progress: Int) {
		progressView.setProgress(progress)
	}

	override fun setSubTasksTime(time: String) {
		// ignore
	}

	override fun setSubTasksTimeVisibility(isVisible: Boolean) {
		// ignore
	}

	override fun setItemList(itemList: List<ViewContract.Item>) {
		if (itemList.isNotEmpty()) {
			item = itemList[0]
			item?.let {
				imageView.loadImage(it.imageUrl)
			}
		}
	}

	override fun showRewardActivity(numCount: Int) {
		RewardActivity.startActivity(this, numCount)
		finish()
	}
}