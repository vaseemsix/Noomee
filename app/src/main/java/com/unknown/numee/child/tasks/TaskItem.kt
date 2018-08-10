package com.unknown.numee.child.tasks


data class TaskItem(
        override val time: String = "",
        override val name: String = "",
        var taskID: String = ""
) : ViewContract.Item