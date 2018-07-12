package com.unknown.numee.business.firebasedb

import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.unknown.numee.business.executor.BusinessCommandCallback
import com.unknown.numee.db.Database
import java.lang.Exception


class FirebaseDatabaseImpl : Database {
    private val firebaseDatabase = FirebaseDatabase.getInstance().reference

    override fun <T> read(tableName: String, ID: String, clazz: Class<T>, callback: BusinessCommandCallback<T>) {
        val reference = if (ID.isEmpty()) {
            firebaseDatabase.child(tableName)
        } else {
            firebaseDatabase.child(tableName).child(ID)
        }
        reference.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onCancelled(databaseError: DatabaseError) {
                callback.onError(Exception(databaseError.message))
            }

            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val result = dataSnapshot.getValue(clazz)
                callback.onSuccess(result)
            }

        })
    }

    override fun <T> write(tableName: String, ID: String, value: T, callback: BusinessCommandCallback<T>) {
        val reference = if (ID.isEmpty()) {
            firebaseDatabase.child(tableName)
        } else {
            firebaseDatabase.child(tableName).child(ID)
        }

        reference.setValue(value)
                .addOnSuccessListener { callback.onSuccess(value) }
                .addOnFailureListener { e -> callback.onError(e) }
    }
}