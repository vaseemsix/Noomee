package com.unknown.numee.parent.subtasks

import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.unknown.numee.business.beans.Task


class SubTasksFirebaseApi {

    private val firebaseDatabase = FirebaseDatabase.getInstance().reference

    fun getTaskByID(
            userID: String,
            scheduleID: String,
            taskID: String,
            onSuccess: (Task?) -> Unit,
            onError: (Exception?) -> Unit
    ) {
        val reference = firebaseDatabase.child("tasks").child(userID).child(scheduleID).child(taskID)

        reference.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onCancelled(databaseError: DatabaseError) {
                onError.invoke(Exception(databaseError.message))
            }

            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val result = dataSnapshot.getValue(Task::class.java)
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
}