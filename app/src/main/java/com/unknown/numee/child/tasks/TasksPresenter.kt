package com.unknown.numee.child.tasks

import com.unknown.numee.business.beans.Task
import com.unknown.numee.util.mvp.Presenter


class TasksPresenter(
        val model: ModelContract.Model
) : Presenter<ViewContract.View>(), ViewContract.Listener, ModelContract.Listener {

    override fun onViewCreated() {
        model.requestTasks(model.currentUserID)
    }

    override fun onReceivedTasksSuccess(tasks: List<Task>?) {

    }
}