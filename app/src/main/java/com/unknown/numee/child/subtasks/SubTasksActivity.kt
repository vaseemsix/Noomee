package com.unknown.numee.child.subtasks

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.WindowManager
import android.widget.ProgressBar
import android.widget.TextView
import com.unknown.numee.R
import com.unknown.numee.base.BaseActivity


class SubTasksActivity : BaseActivity(), ViewContract.View {

    companion object {

        const val ID = "ID"

        fun startActivity(context: Context, id: String) {
            val intent = Intent(context, SubTasksActivity::class.java)
            intent.putExtra(ID, id)
            context.startActivity(intent)
        }
    }

    private lateinit var presenter: ViewContract.Listener

    private var id: String = ""

    private lateinit var titleView: TextView
    private lateinit var subTitleView: TextView
    private lateinit var timeView: TextView
    private lateinit var progressView: ProgressBar
    private lateinit var subTasksListView: RecyclerView
    private lateinit var subTasksListAdapter: SubTasksAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_subtasks)
        window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)

        initExtras(intent.extras)
        initReferences()
        initPresenter()
        presenter.onCreate()
    }

    override fun getID(): String {
        return id
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

    override fun setItemList(itemList: List<ViewContract.Item>) {
        subTasksListAdapter.setItemList(itemList)
    }

    private fun initExtras(extras: Bundle) {
        id = extras.getString(ID, "")
    }

    private fun initReferences() {
        titleView = findViewById(R.id.activity_subtasks__txt_title)
        subTitleView = findViewById(R.id.activity_subtasks__txt_sub_title)
        timeView = findViewById(R.id.activity_subtasks__txt_time)
        progressView = findViewById(R.id.activity_subtasks__progress)

        subTasksListView = findViewById(R.id.activity_subtasks__list_subtasks)
        subTasksListAdapter = SubTasksAdapter(object : SubTasksAdapter.OnClickListener {
            override fun onItemClicked(item: ViewContract.Item) {
                presenter.onItemClicked(item)
            }

        })
        subTasksListView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        subTasksListView.adapter = subTasksListAdapter
    }

    private fun initPresenter() {
        val model = SubTasksModel(this)
        val subTasksPresenter = SubTasksPresenter(model)
        subTasksPresenter.setView(this)
        model.presenter = subTasksPresenter
        presenter = subTasksPresenter
    }
}