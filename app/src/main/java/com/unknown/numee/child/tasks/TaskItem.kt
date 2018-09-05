package com.unknown.numee.child.tasks


data class TaskItem(
        override val time: String = "",
        override val name: String = "",
        override val numCount: String = "",
        override val isDone: Boolean = false,
        var taskID: String = ""
) : ViewContract.Item