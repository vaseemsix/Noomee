package com.unknown.numee.parent.template

import android.util.Log
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.unknown.numee.business.beans.ScheduleTemplates
import com.unknown.numee.business.beans.Task
import java.lang.Exception
import com.unknown.numee.util.Preferences
import java.util.*


class TemplatesFirebaseApi {

    private val firebaseDatabase = FirebaseDatabase.getInstance().reference

    fun getTemplateNames(
            onSuccess: (List<String>?) -> Unit,
            onError: (Exception?) -> Unit
    ) {
        val reference = firebaseDatabase.child("templates")

        reference.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onCancelled(databaseError: DatabaseError) {
                onError.invoke(Exception(databaseError.message))
            }

            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val result: MutableList<String> = mutableListOf()
                if (dataSnapshot.exists()) {
                    for (item in dataSnapshot.children) {
                        val resultItem = item.key + "_'_" + item.child(Preferences.language.toUpperCase()).child("name").getValue(String::class.java)
                        resultItem.let {
                            result.add(it)
                        }
                    }
                }
                onSuccess.invoke(result)
            }

        })
    }

    fun getTemplate(
            templateName: String,
            onSuccess: (List<Task>?) -> Unit,
            onError: (Exception?) -> Unit
    ) {
        val reference = firebaseDatabase.child("templates").child(templateName).child(Preferences.language.toUpperCase()).child("tasks")
        reference.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onCancelled(databaseError: DatabaseError) {
                onError.invoke(Exception(databaseError.message))
            }

            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val result: MutableList<Task> = mutableListOf()
                if (dataSnapshot.exists()) {
                    for (item in dataSnapshot.children) {
                        Log.d("Vlad", item.toString())
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

    fun saveScheduleTask(
            tasksList: List<Task>,
            scheduleName: String,
            onScheduleSaveSuccess: () -> Unit
    ) {
        val scheduleReference = firebaseDatabase.child("schedules").child(Preferences.userID)
        val scheduleId = scheduleReference.push().key
        if (scheduleId != null) {
            var tasksAllId = ""
            for (taskItem in tasksList) {
                val reference = firebaseDatabase.child("tasks").child(Preferences.userID).child(scheduleId)
                val taskId = reference.push().key

                if (taskId != null) {
                    taskItem.id = taskId
                    tasksAllId += taskItem.id + ","
                    reference.child(taskId).setValue(taskItem)
                }
            }

            val scheduleTemplates = ScheduleTemplates()
            scheduleTemplates.id = scheduleId
            scheduleTemplates.name = scheduleName
            scheduleTemplates.tasks = tasksAllId
            scheduleTemplates.date = Date().time / 1000

            scheduleReference.child(scheduleTemplates.id).setValue(scheduleTemplates)

            onScheduleSaveSuccess.invoke()
        }
    }
}