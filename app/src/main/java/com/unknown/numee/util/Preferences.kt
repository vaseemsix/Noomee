package com.unknown.numee.util

import android.content.Context
import android.content.SharedPreferences


object Preferences {

    private const val NAME = "NoomeePrefs"

    private const val USER_ID = "user_id"
    private const val USER_TYPE = "user_type"

    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var editor: SharedPreferences.Editor

    fun initialize(context: Context) {
        sharedPreferences = context.getSharedPreferences(NAME, Context.MODE_PRIVATE)
        editor = sharedPreferences.edit()
    }

    var userID: String
        get() = sharedPreferences.getString(USER_ID, "")
        set(value) = editor.putString(USER_ID, value).apply()

    var userType: String
        get() = sharedPreferences.getString(USER_TYPE, "")
        set(value) = editor.putString(USER_TYPE, value).apply()
}