package com.unknown.numee.util.event

import android.content.Context
import android.content.IntentFilter
import android.support.v4.content.LocalBroadcastManager
import java.util.*
import android.content.Intent


object EventManager {

    const val EXTRA_DATA = "extra_data"
    private const val ACTION = "event_action"

    private lateinit var localBroadcastManager:LocalBroadcastManager
    private val eventReceiver = EventReceiver()
    val eventCallbackMap: MutableMap<Class<*>, MutableList<EventCallback>> = mutableMapOf()

    fun initialize(context: Context) {
        localBroadcastManager = LocalBroadcastManager.getInstance(context)
        val intentFilter = IntentFilter(ACTION)
        localBroadcastManager.registerReceiver(eventReceiver, intentFilter)
    }

    fun <T> register(eventClass: Class<T>, callback: EventCallback) {
        synchronized(eventCallbackMap) {
            var callbackList = eventCallbackMap[eventClass]
            if (callbackList == null) {
                callbackList = LinkedList()
                eventCallbackMap[eventClass] = callbackList
            }
            callbackList.add(callback)
        }
    }

    fun <T> unregister(eventClass: Class<T>, callback: EventCallback) {
        synchronized(eventCallbackMap) {
            val callbackList = eventCallbackMap[eventClass]
            if (callbackList != null) {
                callbackList.remove(callback)
                if (callbackList.isEmpty()) {
                    eventCallbackMap.remove(eventClass)
                }
            }
        }
    }

    fun send(event: Event) {
        val intent = Intent(EventManager.ACTION)
        intent.putExtra(EventManager.EXTRA_DATA, event)
        localBroadcastManager.sendBroadcast(intent)
    }

}