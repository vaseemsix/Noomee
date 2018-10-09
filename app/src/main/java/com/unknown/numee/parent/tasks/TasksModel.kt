package com.unknown.numee.parent.tasks

import android.content.Context
import com.unknown.numee.business.beans.Calendar
import com.unknown.numee.business.beans.Task
import com.unknown.numee.util.mvp.GeneralModel


class TasksModel(context: Context, private val scheduleId: String) : GeneralModel(context), ModelContract.Model {

    lateinit var presenter: ModelContract.Listener
    private val taskApiFirebase = TasksFirebaseApi()

    private var scheduleDays: MutableList<String> = mutableListOf("", "", "", "", "", "", "")
    private var calendar: Calendar = Calendar(mutableListOf())

    override fun updateScheduleDays(position: Int) {
        scheduleDays[position] = scheduleId
    }

    override fun removeScheduleFromDays(position: Int) {
        if (calendar.days.size >= position) {
            if (calendar.days[position] == scheduleId) {
                scheduleDays[position] = ""
            } else {
                scheduleDays[position] = calendar.days[position]
            }
        }
    }

    override fun requestScheduledDays(userID: String) {
        taskApiFirebase.getSchedule(
                userID,
                { result -> presenter.onReceivedCalendarSuccess(result) },
                { e -> presenter.onError(e) }
        )
    }

    override fun updateCalendar(calendar: Calendar) {
        this.calendar = calendar
        scheduleDays = calendar.days
    }

    override fun saveScheduleDays(userId: String) {
        calendar = Calendar(scheduleDays)
        taskApiFirebase.saveSchedule(
                userId,
                scheduleId,
                calendar
        ) { presenter.onReceivedSaveCalendarSuccess() }
    }

    override fun getWeekDays(): MutableList<Boolean> {
        val booleanViewOfScheduleCalendar: MutableList<Boolean> = mutableListOf(false, false, false, false, false, false, false)
        if (scheduleDays.size == booleanViewOfScheduleCalendar.size) {

            for (i in 0 until booleanViewOfScheduleCalendar.size) {
                if (scheduleId == scheduleDays[i]) {
                    booleanViewOfScheduleCalendar[i] = true
                }
            }
        }
        return booleanViewOfScheduleCalendar
    }

    override fun checkForCalendarPresent(userID: String) {
        taskApiFirebase.checkIfSchedulesCalendarExist(
                userID,
                { result -> presenter.onCalendarExist(result) },
                { e -> presenter.onError(e) }
        )
    }


    override fun requestsTasksList(userID: String) {
        taskApiFirebase.getTasksList(
                userID,
                scheduleId,
                { result -> presenter.onReceivedTasksSuccess(result) },
                { e -> presenter.onError(e) }
        )
    }

    private var tasksList: MutableList<Task> = mutableListOf()

    override fun updateTasksList(tasksList: MutableList<Task>) {
        this.tasksList = tasksList
    }

    override fun getTasksList(): MutableList<Task> {
        return this.tasksList
    }

    override fun updateTaskStatus(status: Boolean, position: Int) {
        if (status) {
            tasksList[position].enable = 1
        } else {
            tasksList[position].enable = 0
        }
    }

    override fun saveTasks(userID: String) {
        for (i in 0 until tasksList.size) {
            taskApiFirebase.saveTask(
                    userID,
                    scheduleId,
                    tasksList[i]
            ) { presenter.onReceivedSaveTaskSuccess() }
        }
    }

    override fun saveScheduleName(scheduleName: String, userId: String) {
        taskApiFirebase.saveScheduleName(scheduleName, scheduleId, userId)
    }

}