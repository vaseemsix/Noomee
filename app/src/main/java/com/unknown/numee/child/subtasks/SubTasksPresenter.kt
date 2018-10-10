package com.unknown.numee.child.subtasks

import android.os.CountDownTimer
import com.unknown.numee.business.beans.Status
import com.unknown.numee.business.beans.SubTask
import com.unknown.numee.business.beans.Task
import com.unknown.numee.util.event.EventManager
import com.unknown.numee.util.event.events.TaskFinishedEvent
import com.unknown.numee.util.mvp.Presenter


class SubTasksPresenter(
        val model: ModelContract.Model
) : Presenter<ViewContract.View>(), ViewContract.Listener, ModelContract.Listener {

    override fun onCreate() {
        model.taskID = view.getTaskID()
        model.scheduleID = view.getScheduleID()
        model.requestTaskByID(model.currentUserID, model.scheduleID, model.taskID)
    }

    override fun onItemClicked(item: ViewContract.Item) {
        model.task?.let { task ->
            model.requestUpdateSubTaskStatus(
                    model.currentUserID,
                    task.id,
                    model.scheduleID,
                    item.index,
                    Status.DONE)

            val toDoIndex = getNextToDoIndex(task)
            if (toDoIndex != -1) {
                model.requestUpdateSubTaskStatus(
                        model.currentUserID,
                        task.id,
                        model.scheduleID,
                        toDoIndex.toString(),
                        Status.CURRENT)
            } else {
                finishTask()
            }
        }
    }

    private fun getNextToDoIndex(task: Task): Int {
        var subTasks = task.subTasks
        subTasks = subTasks.sortedBy { it.order }
        return subTasks.indexOfFirst { it.status == Status.TO_DO }
    }

    override fun onError(e: Exception?) {

    }

    override fun onReceivedGetTaskByIDSuccess(task: Task?) {
        model.task = task
        update()
    }

    override fun onReceivedUpdateSubTaskStatusSuccess() {
        model.requestTaskByID(model.currentUserID, model.scheduleID, model.taskID)
    }

    private fun update() {
        val task = model.task ?: return
        if (task.status == Status.DONE) return

        view.setSubTitle(task.name)
        val itemList = createItemList(task)
        val currentSubTask = itemList.find { it.status == 1 }
        currentSubTask?.let {
            view.setTitle(it.name)
        }
        view.setSubTasksProgress(getSubTaskProgress(task.subTasks))
        if (task.duration == 0) {
            view.setSubTasksTimeVisibility(false)
        } else {
            view.setSubTasksTimeVisibility(true)
            startSubTaskTime(task.duration)
        }
        model.itemList = itemList
        view.setItemList(model.itemList)
    }

    private fun createItemList(task: Task): List<ViewContract.Item> {
        val itemList = mutableListOf<ViewContract.Item>()
        var subTasks = task.subTasks
        subTasks = subTasks.sortedBy { it.order }

        val currentSubTaskIndex = subTasks.indexOfFirst { it.status == Status.CURRENT }

        if (currentSubTaskIndex != -1) {
            itemList.add(
                    SubTaskItem(
                            id = subTasks[currentSubTaskIndex].id,
                            name = subTasks[currentSubTaskIndex].name,
                            imageUrl = subTasks[currentSubTaskIndex].imageUrl,
                            status = subTasks[currentSubTaskIndex].status.ordinal,
                            index = currentSubTaskIndex.toString(),
                            doAnimation = !checkIsToiledOrWashing(task.name)
                    )
            )

            if (currentSubTaskIndex + 1 < subTasks.size) {
                itemList.add(
                        SubTaskItem(
                                id = subTasks[currentSubTaskIndex + 1].id,
                                name = subTasks[currentSubTaskIndex + 1].name,
                                imageUrl = subTasks[currentSubTaskIndex + 1].imageUrl,
                                status = subTasks[currentSubTaskIndex + 1].status.ordinal,
                                index = (currentSubTaskIndex + 1).toString(),
                                doAnimation = !checkIsToiledOrWashing(task.name)
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
                        val minutes = seconds / 60
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

    private fun finishTask() {
        model.task?.let {
            model.timer?.cancel()
            EventManager.send(TaskFinishedEvent(it.id))
            view.showRewardActivity(it.numCount)
        }
    }

    // this is temporarily solution for testing purposes
    private fun checkIsToiledOrWashing(name: String): Boolean {
        return name.startsWith("toilet", true)
                || name.startsWith("զուգ", true)
                || name.startsWith("Aller", true)
                || name.startsWith("wash", true)
                || name.startsWith("լվացվել", true)
                || name.startsWith("se laver", true)
    }
}