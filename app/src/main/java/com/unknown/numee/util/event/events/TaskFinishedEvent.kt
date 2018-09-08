package com.unknown.numee.util.event.events

import com.unknown.numee.util.event.Event


data class TaskFinishedEvent(
        val taskID: String
) : Event