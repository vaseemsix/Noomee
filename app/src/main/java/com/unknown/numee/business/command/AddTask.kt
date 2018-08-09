package com.unknown.numee.business.command

import com.unknown.numee.business.beans.Task
import com.unknown.numee.business.executor.BusinessCommand
import com.unknown.numee.business.executor.BusinessCommandCallback
import com.unknown.numee.db.Database


class AddTask(
        val task: Task,
        val callback: BusinessCommandCallback<Task>
) : BusinessCommand<Task>() {

    override fun onExecute(database: Database) {
        database.add("tasks", task, callback)
    }
}