package com.unknown.numee.parent.template

import android.util.Log
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import java.lang.Exception


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
                        val resultItem = item.key
                        resultItem?.let {
                            result.add(it)
                        }
                    }
                }
                onSuccess.invoke(result)
            }

        })
    }
}