package com.unknown.numee.util.extensions

import java.util.*

fun Date.dayOfWeek(): Int {
	val calendar = Calendar.getInstance()
	calendar.time = this
	return calendar.get(Calendar.DAY_OF_WEEK)
}