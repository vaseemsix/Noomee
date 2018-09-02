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

    override fun onItemClicked() {
        // update subtask to done
        update()
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
        val itemList = createItemList(task)
        val currentSubTask = itemList.find { it.status == 1 }
        currentSubTask?.let {
            view.setTitle(it.name)
        }
        model.itemList = itemList
        view.setItemList(model.itemList)
    }

    private fun createItemList(task: Task): List<ViewContract.Item> {
        val itemList = mutableListOf<ViewContract.Item>()
        val subTasks = task.subTasks

        var currentSubTaskIndex = subTasks.indexOfFirst { it.status == Status.CURRENT }
        val lastDoneSubTaskIndex = subTasks.indexOfLast { it.status == Status.DONE }

        currentSubTaskIndex = if (currentSubTaskIndex != -1) {
            currentSubTaskIndex
        } else {
            if (lastDoneSubTaskIndex < itemList.size) {
                lastDoneSubTaskIndex + 1
            } else {
                -1
            }
        }

        if (currentSubTaskIndex == -1) {

        } else {
            itemList.add(
                    SubTaskItem(
                            subTasks[currentSubTaskIndex].name,
                            subTasks[currentSubTaskIndex].imageUrl,
                            subTasks[currentSubTaskIndex].status.ordinal
                    )
            )

            if (currentSubTaskIndex + 1 < subTasks.size) {
                itemList.add(
                        SubTaskItem(
                                subTasks[currentSubTaskIndex + 1].name,
                                subTasks[currentSubTaskIndex + 1].imageUrl,
                                subTasks[currentSubTaskIndex + 1].status.ordinal
                        )
                )
            }
        }

        return itemList
    }
}