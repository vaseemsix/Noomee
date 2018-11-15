package com.unknown.numee.child.tasks

import com.unknown.numee.business.beans.Type


data class TaskItem(
        override val time: String = "",
        override val name: String = "",
        override val numCount: String = "",
        override val statusOrdinal: Int = 0,
        var taskID: String = "",
        var scheduleID: String = "",
        var type: Type = Type.TASK
) : ViewContract.Item