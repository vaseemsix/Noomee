package com.unknown.numee.business.beans

enum class Status {
    TO_DO,
    CURRENT,
    DONE
}

enum class Type {
	TASK,
	STORY
}

data class Task(
        var id: String = "",
        var name: String = "",
        val description: String = "",
        var status: Status = Status.TO_DO,
        val type: Type = Type.TASK,
        val numCount: Int = 1,
        var time: String = "",
        var enable: Int = 1,
        var duration: Int = 1, // in seconds
        var subTasks: List<SubTask> = listOf()
)