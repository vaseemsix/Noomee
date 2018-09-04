package com.unknown.numee.child.subtasks

import com.unknown.numee.business.beans.Status


data class SubTaskItem(
        override val id: String = "",
        override val name: String = "",
        override val imageUrl: String = "",
        override val status: Int = Status.TO_DO.ordinal
) : ViewContract.Item