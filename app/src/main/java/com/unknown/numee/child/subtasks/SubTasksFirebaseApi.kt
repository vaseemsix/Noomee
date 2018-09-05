package com.unknown.numee.child.subtasks

import com.google.firebase.database.FirebaseDatabase
import com.unknown.numee.business.beans.Status
import java.lang.Exception


class SubTasksFirebaseApi {

    private val firebaseDatabase = FirebaseDatabase.getInstance().reference

    fun updateSubTaskStatus(
            taskID: String,
            subTaskID: String,
            status: Status,
            onSuccess: (Status?) -> Unit,
            onError: (Exception?) -> Unit
    ) {
        val reference = firebaseDatabase.child("tasks").child(taskID).child("subTasks").child(subTaskID).child("status")

        reference.setValue(status)
                .addOnSuccessListener { onSuccess.invoke(status) }
                .addOnFailureListener { e -> onError.invoke(e) }
    }
}