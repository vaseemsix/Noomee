package com.unknown.numee.parent.subtasks

import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.widget.*
import com.unknown.numee.R
import com.unknown.numee.base.BaseActivity
import com.unknown.numee.business.beans.Task
import com.unknown.numee.parent.tasks.TasksListActivity

class SubTasksActivity : BaseActivity(), ViewContract.View {

    private lateinit var presenter: ViewContract.Listener

    companion object {

        const val SCHEDULE_ID = "SID"
        const val SCHEDULE_NAME = "SNAME"
        const val TASK_ID = "ID"
        const val TASK_NAME = "NAME"

        fun startActivity(context: Context, id: String, name: String, task_id: String, task_name: String) {
            val intent = Intent(context, SubTasksActivity::class.java)
            intent.putExtra(SCHEDULE_ID, id)
            intent.putExtra(SCHEDULE_NAME, name)
            intent.putExtra(TASK_ID, task_id)
            intent.putExtra(TASK_NAME, task_name)
            context.startActivity(intent)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sub_tasks)

        initPresenter()
    }

    private fun initPresenter() {
        val subTasksModel = SubTasksModel(this)
        val tasksListPresenter = SubTasksListPresenter(this, subTasksModel)
        tasksListPresenter.setView(this)
        subTasksModel.presenter = tasksListPresenter
        presenter = tasksListPresenter
        presenter.onCreate()
    }


    // Variables
    private lateinit var scheduleId: String
    private lateinit var scheduleName: String
    private lateinit var taskId: String
    private lateinit var taskName: String


    private lateinit var taskTitleTextView: TextView
    private lateinit var taskTitleEditTextView: EditText
    private lateinit var taskTitleEditButton: ImageView
    private lateinit var doneButton: ImageView
    private lateinit var closeButton: ImageView
    private lateinit var timePicker: TimePicker

    private lateinit var durationHour: EditText
    private lateinit var durationMinute: EditText
    private lateinit var durationSecond: EditText
    private lateinit var subTaskRecyclerView: RecyclerView

    private lateinit var subTaskAdapter: SubTasksAdapter

    override fun initViews() {

        scheduleId = intent.getStringExtra(SCHEDULE_ID)
        scheduleName = intent.getStringExtra(SCHEDULE_NAME)
        taskId = intent.getStringExtra(TASK_ID)
        taskName = intent.getStringExtra(TASK_NAME)

        taskTitleTextView = findViewById(R.id.task_title)
        taskTitleTextView.text = taskName

        taskTitleEditTextView = findViewById(R.id.task_title_edit)

        taskTitleEditButton = findViewById(R.id.edit_task_name_button)
        taskTitleEditButton.setOnClickListener { presenter.changeView(taskTitleTextView, taskTitleEditTextView) }

        doneButton = findViewById(R.id.done_icon)
        doneButton.setOnClickListener { presenter.saveChanges() }

        closeButton = findViewById(R.id.close_icon)
        closeButton.setOnClickListener { presenter.cancelChanges(scheduleId, scheduleName) }

        timePicker = findViewById(R.id.task_timer_picker)
        timePicker.setIs24HourView(true)

        durationHour = findViewById(R.id.hours)
        durationMinute = findViewById(R.id.minutes)
        durationSecond = findViewById(R.id.seconds)

        subTaskRecyclerView = findViewById(R.id.sub_task_recycler_view)
        subTaskRecyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)

        subTaskAdapter = SubTasksAdapter(this, object : SubTasksAdapter.OnClickListener {
            override fun onSubTaskItemNameClicked(position: Int) {
                presenter.onSubTaskItemNameClicked(position)
            }

            override fun onSwitchClicked(isChecked: Boolean, position: Int) {
                presenter.onSubTaskStatusChange(isChecked, position)
            }
        })

        subTaskRecyclerView.adapter = subTaskAdapter
    }

    override fun getTaskName(): String {
        return taskTitleTextView.text.toString()
    }

    override fun showMessage(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    override fun getScheduleId(): String {
        return scheduleId
    }

    override fun getTaskID(): String {
        return taskId
    }

    override fun getTime(): String {
        val hours: String
        val minutes: String
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            hours = timePicker.hour.toString()
            minutes = timePicker.minute.toString()
        } else {
            hours = timePicker.currentHour.toString()
            minutes = timePicker.currentMinute.toString()
        }
        return "$hours:$minutes"
    }

    override fun getDuration(): Int {
        var duration = 0

        if (durationHour.text.toString() != "") duration += durationHour.text.toString().toInt() * 3600
        if (durationMinute.text.toString() != "") duration += durationMinute.text.toString().toInt() * 60
        if (durationSecond.text.toString() != "") duration += durationSecond.text.toString().toInt()

        return duration
    }

    override fun startTasksListActivity(scheduleId: String, scheduleName: String) {
        finish()
        TasksListActivity.startActivity(this, scheduleId, scheduleName)
    }

    override fun updateAdapter(task: Task) {
        subTaskAdapter.setItemList(task.subTasks)
    }

    override fun updateTaskTime(task: Task) {
        if (task.time.length == 5) {
            val time = task.time.split(":")
            if (time.size == 2) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    timePicker.hour = time[0].toInt()
                    timePicker.minute = time[1].toInt()
                } else {
                    timePicker.currentHour = time[0].toInt()
                    timePicker.currentMinute = time[1].toInt()
                }
            }
        }
    }
}
