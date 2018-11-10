package com.unknown.numee.child.tasks

import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.unknown.numee.business.beans.Calendar
import com.unknown.numee.business.beans.Schedule
import com.unknown.numee.business.beans.Status
import com.unknown.numee.business.beans.Task
import java.util.*


class TasksFirebaseApi {

    private val firebaseDatabase = FirebaseDatabase.getInstance().reference

	fun getCalendar(
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
				if (dataSnapshot.exists()) {
					val result: Calendar? = dataSnapshot.getValue(Calendar::class.java)
					onSuccess.invoke(result)
				}
			}
		})
	}

    fun getSchedule(
		    userID: String,
		    scheduleID: String,
		    onSuccess: (Schedule?) -> Unit,
		    onError: (Exception?) -> Unit
    ) {
        val reference = firebaseDatabase.child("schedules").child(userID).child(scheduleID)

        reference.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onCancelled(databaseError: DatabaseError) {
                onError.invoke(Exception(databaseError.message))
            }

            override fun onDataChange(dataSnapshot: DataSnapshot) {
                if (dataSnapshot.exists()) {
	                val result = dataSnapshot.getValue(Schedule::class.java)
	                onSuccess.invoke(result)
                }
            }

        })
    }

    fun getTasks(
            userID: String,
            scheduleID: String,
            onSuccess: (List<Task>?) -> Unit,
            onError: (Exception?) -> Unit
    ) {
        val reference = firebaseDatabase.child("tasks").child(userID).child(scheduleID)

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

    fun updateTaskStatus(
            userID: String,
            taskID: String,
            scheduleID: String,
            status: Status,
            onSuccess: (Status?) -> Unit,
            onError: (Exception?) -> Unit
    ) {
        val reference = firebaseDatabase.child("tasks").child(userID).child(scheduleID).child(taskID).child("status")

        reference.setValue(status)
                .addOnSuccessListener { onSuccess.invoke(status) }
                .addOnFailureListener { e -> onError.invoke(e) }
    }

    fun updateSubTaskStatus(
            userID: String,
            taskID: String,
            scheduleID: String,
            subTaskIndex: String,
            status: Status,
            onSuccess: (Status?) -> Unit,
            onError: (Exception?) -> Unit
    ) {
        val reference = firebaseDatabase.child("tasks").child(userID).child(scheduleID).child(taskID).child("subTasks").child(subTaskIndex).child("status")

        reference.setValue(status)
                .addOnSuccessListener { onSuccess.invoke(status) }
                .addOnFailureListener { e -> onError.invoke(e) }
    }

    fun resetTasks(
            userID: String,
            taskIDs: String,
            scheduleID: String,
            onSuccess: () -> Unit,
            onError: (Exception?) -> Unit
    ) {
        val reference = firebaseDatabase.child("tasks").child(userID).child(scheduleID)

        val tasks = taskIDs.split(",")
        if (tasks.isNotEmpty()) {
            reference.child(tasks[0]).updateChildren(mapOf(Pair("status", "CURRENT")))

            for (i in 1 until tasks.size) {
                if (tasks[i].isNotEmpty()) {
                    reference.child(tasks[i]).updateChildren(mapOf(Pair("status", "TO_DO")))
                }
            }

            // reset collected nums
            firebaseDatabase
                    .child("users")
                    .child(userID)
                    .child("child")
                    .updateChildren(mapOf(Pair("totalNumCount", 0)))

            // update schedule date
            firebaseDatabase
                    .child("schedules")
                    .child(userID)
                    .child(scheduleID)
                    .updateChildren(mapOf(Pair("date", Date().time)))

            onSuccess.invoke()
        } else {
            onError.invoke(Exception())
        }
    }
}