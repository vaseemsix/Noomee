package com.unknown.numee.business.beans


data class SubTask(
        val id: String = "",
        var name: String = "",
        var imageUrl: String = "",
        var status: Status = Status.TO_DO,
        var enable: Int = 1,
        val order: Int = 0
)