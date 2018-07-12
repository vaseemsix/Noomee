package com.unknown.numee.business.executor

import com.unknown.numee.business.firebasedb.FirebaseDatabaseImpl


class BusinessCommandExecutor {

    private val database = FirebaseDatabaseImpl()

    fun <T> execute(command: BusinessCommand<T>) {
        command.execute(database)
    }
}