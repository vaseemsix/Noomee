package com.unknown.numee.db

import com.unknown.numee.business.executor.BusinessCommandCallback


interface Database {

    fun <T> read(tableName: String, ID: String, clazz: Class<T>, callback: BusinessCommandCallback<T>)

    fun <T> read(query: Query, callback: BusinessCommandCallback<List<T>>, clazz: Class<T>)

    fun <T> update(tableName: String, ID: String, value: T, callback: BusinessCommandCallback<T>)

    fun <T> add(tableName: String, value: T, callback: BusinessCommandCallback<T>)

}