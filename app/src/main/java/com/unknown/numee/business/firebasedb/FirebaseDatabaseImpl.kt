package com.unknown.numee.business.firebasedb

import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.unknown.numee.business.executor.BusinessCommandCallback
import com.unknown.numee.db.*
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

    override fun <T> read(query: Query, callback: BusinessCommandCallback<List<T>>, clazz: Class<T>) {
        val firebaseQuery = convertQueryToFirebaseQuery(query)
        firebaseQuery.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onCancelled(databaseError: DatabaseError) {
                callback.onError(Exception(databaseError.message))
            }

            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val result: MutableList<T> = mutableListOf()
                if (dataSnapshot.exists()) {
                    for (item in dataSnapshot.children) {
                        val resultItem = item.getValue(clazz)
                        resultItem?.let {
                            result.add(it)
                        }
                    }
                }
                callback.onSuccess(result)
            }

        })
    }

    override fun <T> update(tableName: String, ID: String, value: T, callback: BusinessCommandCallback<T>) {
        val reference = firebaseDatabase.child(tableName).child(ID)

        reference.setValue(value)
                .addOnSuccessListener { callback.onSuccess(value) }
                .addOnFailureListener { e -> callback.onError(e) }
    }

    override fun <T> add(tableName: String, value: T, callback: BusinessCommandCallback<T>) {
        val id = firebaseDatabase.child(tableName).push().key.orEmpty()
        val reference = firebaseDatabase.child(tableName).child(id)

        reference.setValue(value)
                .addOnSuccessListener { callback.onSuccess(value) }
                .addOnFailureListener { e -> callback.onError(e) }
    }

    private fun convertQueryToFirebaseQuery(query: Query): com.google.firebase.database.Query {
        val reference = if (query.table.isEmpty()) {
            firebaseDatabase
        } else {
            firebaseDatabase.child(query.table)
        }
        val firebaseQuery = when(query.orderBy) {
            is Child -> reference.orderByChild(query.orderBy.name)
            is Key -> reference.orderByKey()
            is Value -> reference.orderByValue()
        }
        if (query.equalTo.isNotEmpty()) {
            firebaseQuery.equalTo(query.equalTo)
        }

        return firebaseQuery
    }
}