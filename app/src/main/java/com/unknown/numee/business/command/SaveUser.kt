package com.unknown.numee.business.command

import com.unknown.numee.business.beans.User
import com.unknown.numee.business.executor.BusinessCommand
import com.unknown.numee.business.executor.BusinessCommandCallback
import com.unknown.numee.db.Database


class SaveUser(
        val user: User,
        val callback: BusinessCommandCallback<User>
) : BusinessCommand<User>() {

    override fun onExecute(database: Database) {
        database.write("users", user.id, user, callback)
    }
}