package com.unknown.numee.child.subtasks

import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.unknown.numee.business.beans.Status
import com.unknown.numee.business.beans.Task
import java.lang.Exception


class SubTasksFirebaseApi {

    private val firebaseDatabase = FirebaseDatabase.getInstance().reference

    fun getTaskByID(
            userID: String,
            taskID: String,
            onSuccess: (Task?) -> Unit,
            onError: (Exception?) -> Unit
    ) {
        val reference = firebaseDatabase.child("tasks").child(userID).child(taskID)

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

    fun updateSubTaskStatus(
            userID: String,
            taskID: String,
            subTaskID: String,
            status: Status,
            onSuccess: (Status?) -> Unit,
            onError: (Exception?) -> Unit
    ) {
        val reference = firebaseDatabase.child("tasks").child(userID).child(taskID).child("subTasks").child(subTaskID).child("status")

        reference.setValue(status)
                .addOnSuccessListener { onSuccess.invoke(status) }
                .addOnFailureListener { e -> onError.invoke(e) }
    }
}