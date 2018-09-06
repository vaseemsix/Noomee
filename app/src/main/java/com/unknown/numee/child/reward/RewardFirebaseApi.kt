package com.unknown.numee.child.reward

import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import java.lang.Exception


class RewardFirebaseApi {

    private val firebaseDatabase = FirebaseDatabase.getInstance().reference

    fun getTotalNumCount(
            userID: String,
            onSuccess: (Int?) -> Unit,
            onError: (Exception?) -> Unit
    ) {
        val reference = firebaseDatabase.child("users").child(userID).child("child").child("totalNumCount")

        reference.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onCancelled(databaseError: DatabaseError) {
                onError.invoke(Exception(databaseError.message))
            }

            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val result = dataSnapshot.getValue(Int::class.java)
                onSuccess.invoke(result)
            }

        })
    }

    fun setTotalNumCount(
            userID: String,
            value: Int,
            onSuccess: () -> Unit,
            onError: (Exception?) -> Unit
    ) {
        val reference = firebaseDatabase.child("users").child(userID).child("child").child("totalNumCount")

        reference.setValue(value)
                .addOnSuccessListener { onSuccess.invoke() }
                .addOnFailureListener { e -> onError.invoke(e) }
    }
}