package com.unknown.numee.business.command

import com.unknown.numee.business.beans.Schedule
import com.unknown.numee.business.executor.BusinessCommand
import com.unknown.numee.business.executor.BusinessCommandCallback
import com.unknown.numee.db.Child
import com.unknown.numee.db.Database
import com.unknown.numee.db.Query


class GetSchedule(
        private val userID: String,
        private val callback: BusinessCommandCallback<List<Schedule>>
) : BusinessCommand<List<Schedule>>() {

    override fun onExecute(database: Database) {
        val query = Query(
                "schedule",
                Child("userID"),
                userID
        )
        database.read(query, callback, Schedule::class.java)
    }
}