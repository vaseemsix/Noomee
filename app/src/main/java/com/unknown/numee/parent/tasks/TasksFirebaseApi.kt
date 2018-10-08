package com.unknown.numee.parent.tasks

import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.unknown.numee.business.beans.Calendar
import com.unknown.numee.business.beans.Schedule
import com.unknown.numee.business.beans.Task
import java.lang.Exception


class TasksFirebaseApi {

    private val firebaseDatabase = FirebaseDatabase.getInstance().reference

    fun getSchedule(
            userID: String,
            onSuccess: (Calendar?) -> Unit,
            onError: (Exception?) -> Unit
    ) {
        val reference = firebaseDatabase.child("calendar").child(userID)

        reference.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onCancelled(databaseError: DatabaseError) {
                onError.invoke(Exception(databaseError.message))
            }

            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val result: Calendar? = dataSnapshot.getValue(Calendar::class.java)
                onSuccess.invoke(result)
            }

        })
    }

    fun saveSchedule(
            userID: String,
            scheduleId: String,
            calendar: Calendar,
            onSuccess: () -> Unit
    ) {
        firebaseDatabase.child("calendar").child(userID).setValue(calendar)
        onSuccess.invoke()
    }

    fun checkIfSchedulesCalendarExist(
            userID: String,
            onSuccess: (Boolean?) -> Unit,
            onError: (Exception?) -> Unit
    ) {
        val reference = firebaseDatabase.child("calendar").child(userID)

        reference.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onCancelled(databaseError: DatabaseError) {
                onError.invoke(Exception(databaseError.message))
            }

            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val result: Boolean?
                result = dataSnapshot.exists()
                onSuccess.invoke(result)
            }
        })


    }


    fun getTasksList(
            userID: String,
            scheduleId: String,
            onSuccess: (MutableList<Task>?) -> Unit,
            onError: (Exception?) -> Unit
    ) {
        val reference = firebaseDatabase.child("tasks").child(userID).child(scheduleId)

        reference.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onCancelled(databaseError: DatabaseError) {
                onError.invoke(Exception(databaseError.message))
            }

            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val result: MutableList<Task> = mutableListOf()
                if (dataSnapshot.exists()) {
                    for (item in dataSnapshot.children) {
                        val resultItem = item.getValue(Task::class.java)
                        resultItem?.let {
                            result.add(it)
                        }
                    }
                }
                onSuccess.invoke(result)
            }

        })
    }

    fun saveTask(
            userID: String,
            scheduleId: String,
            task: Task,
            onSuccess: () -> Unit
    ) {
        firebaseDatabase.child("tasks").child(userID).child(scheduleId).child(task.id).setValue(task)
        onSuccess.invoke()
    }

    fun saveScheduleName(scheduleName: String, scheduleId: String, userID: String) {
        firebaseDatabase.child("schedules").child(userID).child(scheduleId).child("name").setValue(scheduleName)
    }
}