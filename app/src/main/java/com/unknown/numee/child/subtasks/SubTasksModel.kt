package com.unknown.numee.child.subtasks

import android.content.Context
import com.unknown.numee.business.beans.Task
import com.unknown.numee.business.command.GetTaskByID
import com.unknown.numee.business.executor.BusinessCommandCallback
import com.unknown.numee.util.mvp.GeneralModel
import java.lang.Exception


class SubTasksModel(context: Context) : GeneralModel(context), ModelContract.Model {

    lateinit var presenter: ModelContract.Listener

    override var task: Task? = null

    override fun requestTaskByID(ID: String) {
        businessCommandExecutor.execute(
                GetTaskByID(ID, object : BusinessCommandCallback<Task> {
                    override fun onSuccess(result: Task?) {
                        presenter.onReceivedGetTaskByIDSuccess(result)
                    }

                    override fun onError(e: Exception) {
                        presenter.onError(e)
                    }
                })
        )
    }
}