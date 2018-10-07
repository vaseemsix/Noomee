package com.unknown.numee.parent.tasks

import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.unknown.numee.business.beans.Schedule
import com.unknown.numee.util.Preferences
import java.lang.Exception


class TasksFirebaseApi {

    private val firebaseDatabase = FirebaseDatabase.getInstance().reference

    fun getSchedules(
            userID: String,
            onSuccess: (List<Schedule>?) -> Unit,
            onError: (Exception?) -> Unit
    ) {
        val reference = firebaseDatabase.child("schedules").child(userID)

        reference.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onCancelled(databaseError: DatabaseError) {
                onError.invoke(Exception(databaseError.message))
            }

            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val result: MutableList<Schedule> = mutableListOf()
                if (dataSnapshot.exists()) {
                    for (item in dataSnapshot.children) {
                        val resultItem = item.getValue(Schedule::class.java)
                        resultItem?.let {
                            result.add(it)
                        }
                    }
                }
                onSuccess.invoke(result)
            }

        })
    }

    fun removeSchedule(
            schedule: Schedule,
            onSuccess: () -> Unit
    ) {
        firebaseDatabase.child("schedules").child(Preferences.userID).child(schedule.id).removeValue()
        firebaseDatabase.child("tasks").child(Preferences.userID).child(schedule.id).removeValue()
        onSuccess.invoke()
    }
}