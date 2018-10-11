package com.unknown.numee.parent.tasks

import android.content.Context
import android.support.v4.content.ContextCompat
import android.view.View
import android.widget.EditText
import android.widget.TextView
import com.unknown.numee.R
import com.unknown.numee.business.beans.Calendar
import com.unknown.numee.business.beans.Task
import com.unknown.numee.util.Preferences
import com.unknown.numee.util.mvp.Presenter
import java.lang.Exception


class TasksListPresenter(
        val context: Context,
        val model: ModelContract.Model
) : Presenter<ViewContract.View>(), ViewContract.Listener, ModelContract.Listener {

    override fun onCreate() {
        view.initViews()
        model.checkForCalendarPresent(Preferences.userID)
        model.requestsTasksList(Preferences.userID)
    }

    override fun onDayItemClicked(textView: TextView, position: Int) {
        if (textView.currentTextColor != ContextCompat.getColor(context, R.color.colorRewardBg)) {
            textView.setTextColor(ContextCompat.getColor(context, R.color.colorRewardBg ))
            textView.background = ContextCompat.getDrawable(context, R.drawable.styles__button_type_3_on)
            model.updateScheduleDays(position)
        } else {
            textView.setTextColor(ContextCompat.getColor(context, R.color.colorParentPageTitle ))
            textView.background = ContextCompat.getDrawable(context, R.drawable.styles__button_type_3_off)
            model.removeScheduleFromDays(position)
        }
    }

    override fun onSaveClick() {
        model.saveScheduleDays(Preferences.userID)
        model.saveScheduleName(view.getScheduleName(), Preferences.userID)
        model.saveTasks(Preferences.userID)
    }

    override fun onReceivedSaveCalendarSuccess() {
        view.showMessage("Schedule Saved")
    }

    override fun onCalendarExist(calendarExist: Boolean?) {
        if ((calendarExist != null) && (calendarExist)) {
            model.requestScheduledDays(Preferences.userID)
        } else {
            view.updateWeekDays(model.getWeekDays())
        }
    }

    override fun onReceivedCalendarSuccess(calendar: Calendar?) {
        if (calendar != null) {
            model.updateCalendar(calendar)
            view.updateWeekDays(model.getWeekDays())
        }
    }

    override fun onError(e: Exception?) {
        if (e != null) {
            view.showMessage(e.message.toString())
        }
    }

    override fun onReceivedTasksSuccess(tasksList: MutableList<Task>?) {
        if (tasksList != null) {
            model.updateTasksList(tasksList)
            view.updateTasksList(tasksList)
        }
    }

    override fun onTaskStatusChange(status: Boolean, position: Int) {
        model.updateTaskStatus(status, position)
    }

    override fun onReceivedSaveTaskSuccess() {}

    override fun onTaskItemClicked(position: Int) {
        view.startSubTaskActivity(model.getTasksList()[position].id, model.getTasksList()[position].name)
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

    override fun onAllTasksSaved() {
        view.startMainActivity()
    }

}