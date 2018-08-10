package com.unknown.numee.business.command

import com.unknown.numee.business.beans.Task
import com.unknown.numee.business.executor.BusinessCommand
import com.unknown.numee.business.executor.BusinessCommandCallback
import com.unknown.numee.db.Database


class GetTaskByID(
        val ID: String,
        val callback: BusinessCommandCallback<Task>
) : BusinessCommand<Task>() {

    override fun onExecute(database: Database) {
        database.read("tasks", ID, Task::class.java, callback)
    }
}