package com.unknown.numee.child.subtasks

import android.os.CountDownTimer
import com.unknown.numee.business.beans.Status
import com.unknown.numee.business.beans.SubTask
import com.unknown.numee.business.beans.Task
import com.unknown.numee.util.mvp.Presenter
import java.lang.Exception


class SubTasksPresenter(
        val model: ModelContract.Model
) : Presenter<ViewContract.View>(), ViewContract.Listener, ModelContract.Listener {

    override fun onCreate() {
        model.requestTaskByID(view.getID())
    }

    override fun onItemClicked(item: ViewContract.Item) {
        model.task?.let { task ->
            model.requestUpdateSubTaskStatus(
                    task.id,
                    item.id,
                    Status.DONE
            )

            val toDo = task.subTasks.find { it.status == Status.TO_DO }
            if (toDo != null) {
                model.requestUpdateSubTaskStatus(
                        task.id,
                        toDo.id,
                        Status.CURRENT
                )
            } else {
                view.showRewardActivity(task.numCount)
                model.timer?.cancel()
            }
        }
    }

    override fun onError(e: Exception?) {

    }

    override fun onReceivedGetTaskByIDSuccess(task: Task?) {
        model.task = task
        update()
    }

    override fun onReceivedUpdateSubTaskStatusSuccess() {
        model.requestTaskByID(view.getID())
    }

    private fun update() {
        val task = model.task ?: return

        view.setSubTitle(task.name)
        val itemList = createItemList(task)
        val currentSubTask = itemList.find { it.status == 1 }
        currentSubTask?.let {
            view.setTitle(it.name)
        }
        view.setSubTasksProgress(getSubTaskProgress(task.subTasks))
        startSubTaskTime(task.time)
        model.itemList = itemList
        view.setItemList(model.itemList)
    }

    private fun createItemList(task: Task): List<ViewContract.Item> {
        val itemList = mutableListOf<ViewContract.Item>()
        val subTasks = task.subTasks

        val currentSubTaskIndex = subTasks.indexOfFirst { it.status == Status.CURRENT }

        if (currentSubTaskIndex != -1) {
            itemList.add(
                    SubTaskItem(
                            subTasks[currentSubTaskIndex].id,
                            subTasks[currentSubTaskIndex].name,
                            subTasks[currentSubTaskIndex].imageUrl,
                            subTasks[currentSubTaskIndex].status.ordinal
                    )
            )

            if (currentSubTaskIndex + 1 < subTasks.size) {
                itemList.add(
                        SubTaskItem(
                                subTasks[currentSubTaskIndex + 1].id,
                                subTasks[currentSubTaskIndex + 1].name,
                                subTasks[currentSubTaskIndex + 1].imageUrl,
                                subTasks[currentSubTaskIndex + 1].status.ordinal
                        )
                )
            }
        }

        return itemList
    }

    private fun getSubTaskProgress(subTasks: List<SubTask>): Int {
        val doneCount = subTasks.count { it.status == Status.DONE }
        return ((doneCount.toFloat() / subTasks.size) * 100).toInt()
    }

    private fun startSubTaskTime(time: Int) {
        if (model.timer == null) {
            model.timer = object : CountDownTimer((time * 1000).toLong(), 1000) {
                override fun onTick(millisUntilFinished: Long) {
                    val seconds = millisUntilFinished / 1000
                    val timeValue = if (seconds < 60) {
                        "00:$seconds"
                    } else {
                        val minutes = seconds % 60
                        val remainingSeconds = seconds - (minutes * 60)
                        "$minutes:$remainingSeconds"
                    }
                    view.setSubTasksTime(timeValue)
                }

                override fun onFinish() {

                }
            }
            model.timer?.start()
        }
    }
}