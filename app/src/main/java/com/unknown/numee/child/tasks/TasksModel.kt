package com.unknown.numee.child.tasks

import android.content.Context
import com.unknown.numee.business.beans.Schedule
import com.unknown.numee.business.beans.Task
import com.unknown.numee.business.command.GetSchedule
import com.unknown.numee.business.command.GetTasks
import com.unknown.numee.business.executor.BusinessCommandCallback
import com.unknown.numee.util.Preferences
import com.unknown.numee.util.mvp.GeneralModel
import java.lang.Exception


class TasksModel(context: Context) : GeneralModel(context), ModelContract.Model {

    lateinit var presenter: ModelContract.Listener

    override val currentUserID: String
        get() = Preferences.userID
    override var schedule: Schedule? = null

    override fun requestSchedule(userID: String) {
        businessCommandExecutor.execute(
                GetSchedule(userID, object : BusinessCommandCallback<List<Schedule>> {
                    override fun onSuccess(result: List<Schedule>?) {
                        presenter.onReceivedScheduleSuccess(result)
                    }

                    override fun onError(e: Exception) {
                        presenter.onError(e)
                    }
                })
        )
    }

    override fun requestTasks(userID: String) {
        businessCommandExecutor.execute(
                GetTasks(userID, object : BusinessCommandCallback<List<Task>> {
                    override fun onSuccess(result: List<Task>?) {
                        presenter.onReceivedTasksSuccess(result)
                    }

                    override fun onError(e: Exception) {
                        presenter.onError(e)
                    }
                })
        )
    }
}