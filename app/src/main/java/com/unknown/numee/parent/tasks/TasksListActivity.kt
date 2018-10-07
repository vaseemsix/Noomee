package com.unknown.numee.parent.tasks

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.widget.Toast
import com.unknown.numee.R
import com.unknown.numee.base.BaseActivity

class TasksListActivity : BaseActivity(), ViewContract.View {

    companion object {

        const val ID = "ID"

        fun startActivity(context: Context, id: String) {
            val intent = Intent(context, TasksListActivity::class.java)
            intent.putExtra(ID, id)
            context.startActivity(intent)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tasks_list)

        initPresenter()
    }

    private lateinit var presenter: ViewContract.Listener
    private lateinit var weekDaysRecyclerView: RecyclerView
    private lateinit var weekDaysAdapter: WeekDaysAdapter

    override fun initViews() {
        weekDaysRecyclerView = findViewById(R.id.week_days_view_area)
        weekDaysRecyclerView.layoutManager = LinearLayoutManager(this)

        weekDaysAdapter = WeekDaysAdapter(object : WeekDaysAdapter.OnClickListener {
            override fun onDaysItemClicked(item: Int) {
                presenter.onDayItemClicked(item)
            }
        })

        weekDaysRecyclerView.adapter = weekDaysAdapter
    }

    private fun initPresenter() {
        val tasksModel = TasksModel(this)
        val tasksListPresenter = TasksListPresenter(tasksModel)
        tasksListPresenter.setView(this)
        tasksModel.presenter = tasksListPresenter
        presenter = tasksListPresenter
        presenter.onCreate()
    }

    override fun showMessage(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}
