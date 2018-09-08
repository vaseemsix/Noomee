package com.unknown.numee.util.event

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent


class EventReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context?, intent: Intent?) {
        val event = intent?.getSerializableExtra(EventManager.EXTRA_DATA) as Event
        val callbacks = mutableListOf<EventCallback>()
        synchronized(EventManager.eventCallbackMap) {
            if (EventManager.eventCallbackMap.containsKey(event::class.java)) {
                val callbackList = EventManager.eventCallbackMap[event.javaClass]
                callbackList?.let {
                    callbacks.addAll(0, callbackList)
                }
            }
        }
        for (callback in callbacks) {
            callback.onReceived(event)
        }
    }
}