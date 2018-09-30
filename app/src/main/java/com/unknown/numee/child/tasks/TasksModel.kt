package com.unknown.numee.child.tasks

import android.content.Context
import com.unknown.numee.business.beans.Schedule
import com.unknown.numee.business.beans.Status
import com.unknown.numee.business.beans.Task
import com.unknown.numee.business.beans.User
import com.unknown.numee.business.command.GetUser
import com.unknown.numee.business.executor.BusinessCommandCallback
import com.unknown.numee.util.Preferences
import com.unknown.numee.util.mvp.GeneralModel
import java.lang.Exception


class TasksModel(context: Context) : GeneralModel(context), ModelContract.Model {

    lateinit var presenter: ModelContract.Listener

    override val currentUserID: String
        get() = Preferences.userID
    override var schedule: Schedule? = null
    override var tasks: List<Task>? = listOf()
    override var totalNumCount: Int = 0

    override fun getStringById(resId: Int): String {
        return context.getString(resId)
    }

    private val tasksApiFirebase = TasksFirebaseApi()

    override fun getUser(ID: String) {
        businessCommandExecutor.execute(
                GetUser(ID,
                        object : BusinessCommandCallback<User> {
                            override fun onSuccess(result: User?) {
                                presenter.onReceivedGetUserSuccess(result)
                            }

                            override fun onError(e: Exception) {
                                presenter.onError(e)
                            }

                        })
        )
    }

    override fun requestSchedules(userID: String) {
        tasksApiFirebase.getSchedules(
                userID,
                { result -> presenter.onReceivedScheduleSuccess(result) },
                { e -> presenter.onError(e) }
        )
    }

    override fun requestTasks(userID: String) {
        tasksApiFirebase.getTasks(
                userID,
                { result -> presenter.onReceivedTasksSuccess(result) },
                { e -> presenter.onError(e) }
        )
    }

    override fun requestResetTasks(userID: String, taskIDs: String, scheduleID: String) {
        tasksApiFirebase.resetTasks(
                userID,
                taskIDs,
                scheduleID,
                { presenter.onReceivedResetTasksSuccess() },
                { e -> presenter.onError(e) }
        )
    }

    override fun requestUpdateTaskStatus(userID: String, taskID: String, newStatus: Status) {
        tasksApiFirebase.updateTaskStatus(
                userID,
                taskID,
                newStatus,
                { presenter.onReceivedUpdateTaskStatusSuccess() },
                { exception -> presenter.onError(exception) }
        )
    }

    override fun requestUpdateSubTaskStatus(userID: String, taskID: String, subTaskID: String, subTaskIndex: String, newStatus: Status) {
        tasksApiFirebase.updateSubTaskStatus(
                userID,
                taskID,
                subTaskID,
                subTaskIndex,
                newStatus,
                { presenter.onReceivedUpdateSubTaskStatusSuccess() },
                { exception -> presenter.onError(exception) }
        )
    }
}