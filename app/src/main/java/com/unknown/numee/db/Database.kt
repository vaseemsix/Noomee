package com.unknown.numee.db

import com.unknown.numee.business.executor.BusinessCommandCallback


interface Database {

    fun <T> read(tableName: String, ID: String, clazz: Class<T>, callback: BusinessCommandCallback<T>)

    fun <T> write(tableName: String, ID: String, value: T, callback: BusinessCommandCallback<T>)

}