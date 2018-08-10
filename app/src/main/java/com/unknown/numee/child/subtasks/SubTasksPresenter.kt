package com.unknown.numee.child.subtasks

import com.unknown.numee.business.beans.Status
import com.unknown.numee.business.beans.Task
import com.unknown.numee.util.mvp.Presenter
import java.lang.Exception


class SubTasksPresenter(
        val model: ModelContract.Model
) : Presenter<ViewContract.View>(), ViewContract.Listener, ModelContract.Listener {

    override fun onCreate() {
        model.requestTaskByID(view.getID())
    }

    override fun onError(e: Exception) {

    }

    override fun onReceivedGetTaskByIDSuccess(task: Task?) {
        model.task = task
        update()
    }

    private fun update() {
        val task = model.task ?: return

        view.setSubTitle(task.name)
        view.setItemList(createItemList(task))
    }

    private fun createItemList(task: Task): List<ViewContract.Item> {
        val itemList = mutableListOf<ViewContract.Item>()

        task.subTasks.forEach {
            itemList.add(
                    SubTaskItem(
                            it.name,
                            it.imageUrl,
                            it.status.ordinal
                    )
            )
        }

        return itemList
    }
}