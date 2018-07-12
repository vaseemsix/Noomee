package com.unknown.numee.business.executor

import com.unknown.numee.db.Database


abstract class BusinessCommand<Result> {

    fun execute(database: Database) {
        return onExecute(database)
    }

    abstract fun onExecute(database: Database)

}