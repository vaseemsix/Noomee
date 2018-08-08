package com.unknown.numee.child.tasks


data class TaskItem(
        override val time: String = "",
        override val name: String = ""
) : ViewContract.Item