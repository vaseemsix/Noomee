package com.unknown.numee.parent.tasks

import android.widget.EditText
import android.widget.TextView
import com.unknown.numee.business.beans.Task

interface ViewContract {
    interface View {
        fun initViews()
        fun showMessage(message: String)
        fun updateWeekDays(enabledWeekDays: MutableList<Boolean>)
        fun updateTasksList(tasksList: MutableList<Task>)
        fun startSubTaskActivity(taskId: String, taskName: String)
        fun startMainActivity()
        fun getScheduleName(): String
    }

    interface Listener {
        fun onCreate()
        fun onDayItemClicked(textView: TextView, position: Int)
        fun onTaskItemClicked(position: Int)
        fun onSaveClick()
        fun onTaskStatusChange(status: Boolean, position: Int)
        fun changeView(textView: TextView, editText: EditText)
    }
}