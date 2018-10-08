package com.unknown.numee.business.beans


data class SubTask(
        val id: String = "",
        val name: String = "",
        val imageUrl: String = "",
        val status: Status = Status.TO_DO,
        val enable: Int = 1,
        val order: Int = 0
)