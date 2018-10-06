package com.unknown.numee.child.push

import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.unknown.numee.util.Preferences

class ChildFirebaseMessagingService : FirebaseMessagingService() {

    override fun onMessageReceived(remoteMessage: RemoteMessage?) {
        remoteMessage?.notification?.let {
        }
    }

    override fun onNewToken(token: String?) {
        token?.let {
            Preferences.userToken = it
        }
    }
}