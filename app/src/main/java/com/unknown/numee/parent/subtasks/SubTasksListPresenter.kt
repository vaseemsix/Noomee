package com.unknown.numee.parent.subtasks

import android.content.Context
import android.view.View
import android.widget.EditText
import android.widget.TextView
import com.unknown.numee.business.beans.Task
import com.unknown.numee.util.Preferences
import com.unknown.numee.util.mvp.Presenter


class SubTasksListPresenter(
        val context: Context,
        val model: ModelContract.Model
) : Presenter<ViewContract.View>(), ViewContract.Listener, ModelContract.Listener {

    override fun onCreate() {
        view.initViews()
        model.requestTaskByID(Preferences.userID, view.getScheduleId(), view.getTaskID())
    }

    override fun changeView(textView: TextView, editText: EditText) {
        if (textView.isShown) {
            textView.visibility = View.INVISIBLE
            editText.visibility = View.VISIBLE
            editText.hint = textView.text
        } else {
            textView.visibility = View.VISIBLE
            editText.visibility = View.INVISIBLE
            if (editText.text.toString() != "") {
                textView.text = editText.text
            }
        }
    }

    override fun cancelChanges(scheduleId: String, scheduleName: String) {
        view.startTasksListActivity()
    }

    override fun saveChanges() {
        model.updateTaskTime(view.getTime())
        model.updateTaskDuration(view.getDuration())
        model.updateTaskName(view.getTaskName())
        model.saveTask(Preferences.userID, view.getScheduleId())
    }

    override fun onSubTaskStatusChange(isChecked: Boolean, position: Int) {
        model.updateTaskStatus(isChecked, position)
    }

    override fun onSubTaskItemNameClicked(position: Int) {
//        model.updateSubTaskName()
    }

    override fun onError(e: Exception?) {
        if (e != null) {
            view.showMessage(e.message.toString())
        }
    }

    override fun onReceivedGetTaskByIDSuccess(task: Task?) {
        if (task != null) {
            model.updateTask(task)
            view.updateTaskTime(model.getTask())
            view.updateAdapter(model.getTask())
        }
    }

    override fun onReceivedSaveTaskSuccess() {
        view.startTasksListActivity()
    }

    override fun onCameraIconClick(position: Int) {
        view.showMessage("Will Be Available Soon")
    }

    override fun onRecordIconClick(position: Int) {
        view.showMessage("Will Be Available Soon")
    }

}