package com.unknown.numee.parent.tasks

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.unknown.numee.R
import com.unknown.numee.base.BaseActivity
import com.unknown.numee.business.beans.Task
import com.unknown.numee.parent.subtasks.SubTasksActivity

class TasksListActivity : BaseActivity(), ViewContract.View {

    companion object {

        const val ID = "ID"
        const val NAME = "NAME"

        fun startActivity(context: Context, id: String, name: String) {
            val intent = Intent(context, TasksListActivity::class.java)
            intent.putExtra(ID, id)
            intent.putExtra(NAME, name)
            context.startActivity(intent)
        }
    }

    private lateinit var scheduleId: String
    private lateinit var scheduleName: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tasks_list)

        scheduleId = intent.getStringExtra(ID)
        scheduleName = intent.getStringExtra(NAME)

        initPresenter()
    }

    private lateinit var presenter: ViewContract.Listener
    private lateinit var weekDaysRecyclerView: RecyclerView
    private lateinit var weekDaysAdapter: WeekDaysAdapter
    private lateinit var tasksRecyclerView: RecyclerView
    private lateinit var tasksAdapter: TasksAdapter
    private lateinit var title: TextView
    private lateinit var titleEditView: EditText
    private lateinit var titleEditButton: ImageView

    public fun saveSchedule(view: View) {
        presenter.onSaveClick()
    }

    override fun initViews() {
        title = findViewById(R.id.scheduleNameId)
        title.text = scheduleName

        titleEditButton = findViewById(R.id.edit_icon_view)
        titleEditView = findViewById(R.id.edit_title_view)

        titleEditButton.setOnClickListener { presenter.changeView(title, titleEditView) }

        weekDaysRecyclerView = findViewById(R.id.week_days_view_area)
        weekDaysRecyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)

        weekDaysAdapter = WeekDaysAdapter(this, object : WeekDaysAdapter.OnClickListener {
            override fun onDaysItemClicked(textView: TextView, position: Int) {
                presenter.onDayItemClicked(textView, position)
            }
        })

        weekDaysRecyclerView.adapter = weekDaysAdapter

        tasksRecyclerView = findViewById(R.id.tasks_area)
        tasksRecyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)

        tasksAdapter = TasksAdapter(this, object : TasksAdapter.OnClickListener {
            override fun onTaskItemClicked(position: Int) {
                presenter.onTaskItemClicked(position)
            }

            override fun onSwitchClicked(isChecked: Boolean, position: Int) {
                presenter.onTaskStatusChange(isChecked, position)
            }
        })

        tasksRecyclerView.adapter = tasksAdapter
    }

    override fun updateWeekDays(enabledWeekDays: MutableList<Boolean>) {
        weekDaysAdapter.setItemList(enabledWeekDays, resources.getStringArray(R.array.week_days))
    }

    private fun initPresenter() {
        val tasksModel = TasksModel(this, scheduleId)
        val tasksListPresenter = TasksListPresenter(this, tasksModel)
        tasksListPresenter.setView(this)
        tasksModel.presenter = tasksListPresenter
        presenter = tasksListPresenter
        presenter.onCreate()
    }

    override fun showMessage(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    override fun updateTasksList(tasksList: MutableList<Task>) {
        tasksAdapter.setItemList(tasksList)
    }

    override fun startSubTaskActivity(taskId: String, taskName: String) {
        finish()
        SubTasksActivity.startActivity(this, taskId, taskName)
    }

    override fun getScheduleName(): String {
        return title.text.toString()
    }
}
