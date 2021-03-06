package com.unknown.numee.parent.subtasks

import android.widget.EditText
import android.widget.TextView
import com.unknown.numee.business.beans.Task

interface ViewContract {
    interface View {
        fun initViews()
        fun startTasksListActivity()
        fun getTime(): String
        fun getDuration(): Int
        fun showMessage(message: String)

        fun getScheduleId(): String
        fun getTaskID(): String

        fun updateAdapter(task: Task)
        fun updateTaskTime(task: Task)

        fun getTaskName(): String
    }

    interface Listener {
        fun onCreate()
        fun changeView(textView: TextView, editText: EditText)
        fun saveChanges()
        fun cancelChanges(scheduleId: String, scheduleName: String)
        fun onSubTaskItemNameClicked(position: Int)
        fun onSubTaskStatusChange(isChecked: Boolean, position: Int)
        fun onRecordIconClick(position: Int)
        fun onCameraIconClick(position: Int)
    }
}