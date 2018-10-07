package com.unknown.numee.child.push

import com.google.firebase.database.FirebaseDatabase

class ChildPushNotificationFirebaseApi {

    private val firebaseDatabase = FirebaseDatabase.getInstance().reference

    fun setUserToken(
            userID: String,
            token: String
    ) {
        firebaseDatabase.child("tokens").child(userID).setValue(token)
    }
}