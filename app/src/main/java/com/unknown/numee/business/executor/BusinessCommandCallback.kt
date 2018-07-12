package com.unknown.numee.business.executor

import java.lang.Exception


interface BusinessCommandCallback<Result> {
    fun onSuccess(result: Result?)
    fun onError(e: Exception)
}