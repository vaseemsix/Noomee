package com.unknown.numee.util

import android.content.Context


class UserPreferences(
		context: Context
) {

	companion object {
		const val NAME = "NoomeeUserPreferences"

		const val USER_ID = "user_id"
		const val USER_NAME = "user_name"
		const val CHILD_NAME = "child_name"
	}

	private val sharedPrefsUtil: SharedPrefsUtil

	init {
		sharedPrefsUtil = SharedPrefsUtil(context, NAME)
	}

	data class LocalUser(
			val id: String,
			val name: String,
			val childName: String
	)

	fun getUser(): LocalUser {
		return LocalUser(
				id = sharedPrefsUtil.getString(USER_ID, ""),
				name = sharedPrefsUtil.getString(USER_NAME, ""),
				childName = sharedPrefsUtil.getString(CHILD_NAME, "")
		)
	}

	fun saveUser(user: LocalUser) {
		sharedPrefsUtil.putString(USER_ID, user.id)
		sharedPrefsUtil.putString(USER_NAME, user.name)
		sharedPrefsUtil.putString(CHILD_NAME, user.childName)
	}


}