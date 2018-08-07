package com.unknown.numee.business.command

import com.unknown.numee.business.beans.Task
import com.unknown.numee.business.executor.BusinessCommand
import com.unknown.numee.business.executor.BusinessCommandCallback
import com.unknown.numee.db.Child
import com.unknown.numee.db.Database
import com.unknown.numee.db.Query


class GetTasks(
        private val userID: String,
        private val callback: BusinessCommandCallback<List<Task>>
) : BusinessCommand<List<Task>>() {

    override fun onExecute(database: Database) {
        val query = Query(
                "tasks",
                Child("userID"),
                userID
        )
        database.read(query, callback, Task::class.java)
    }
}