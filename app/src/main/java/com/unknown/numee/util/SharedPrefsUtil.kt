package com.unknown.numee.util

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences

@SuppressLint("CommitPrefEdits")
class SharedPrefsUtil(
		context: Context,
		name: String
) {

	private var sharedPreference: SharedPreferences = context.getSharedPreferences(name, Context.MODE_PRIVATE)
	private var editor: SharedPreferences.Editor

	init {
		editor = sharedPreference.edit()
	}

	fun putString(key: String, value: String) {
		editor.putString(key, value)
		editor.apply()
	}

	fun getString(key: String, defaultValue: String): String {
		return sharedPreference.getString(key, defaultValue)
	}

	fun putBoolean(key: String, value: Boolean) {
		editor.putBoolean(key, value)
		editor.apply()
	}

	fun getBoolean(key: String, defaultValue: Boolean): Boolean {
		return sharedPreference.getBoolean(key, defaultValue)
	}

	fun putInt(key: String, value: Int) {
		editor.putInt(key, value)
		editor.apply()
	}

	fun getInt(key: String, defaultValue: Int): Int {
		return sharedPreference.getInt(key, defaultValue)
	}

	fun putLong(key: String, value: Long) {
		editor.putLong(key, value)
		editor.apply()
	}

	fun getLong(key: String, defaultValue: Long): Long {
		return sharedPreference.getLong(key, defaultValue)
	}

	fun putFloat(key: String, value: Float) {
		editor.putFloat(key, value)
		editor.apply()
	}

	fun getFloat(key: String, defaultValue: Float): Float {
		return sharedPreference.getFloat(key, defaultValue)
	}

	fun exists(key: String): Boolean {
		return sharedPreference.contains(key)
	}

	fun clear() {
		editor.clear()
		editor.apply()
	}
}