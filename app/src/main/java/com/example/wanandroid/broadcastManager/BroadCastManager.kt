package com.example.wanandroid.broadcastManager

import android.app.Activity
import android.content.BroadcastReceiver
import android.content.Intent
import android.content.IntentFilter


class BroadCastManager {
    private val broadCastManager = BroadCastManager()

    fun getInstance(): BroadCastManager? {
        return broadCastManager
    }

    //注册广播接收者
    fun registerReceiver(
        activity: Activity,
        receiver: BroadcastReceiver?, filter: IntentFilter?
    ) {
        activity.registerReceiver(receiver, filter)
    }

    //注销广播接收者
    fun unregisterReceiver(
        activity: Activity,
        receiver: BroadcastReceiver?
    ) {
        activity.unregisterReceiver(receiver)
    }

    //发送广播
    fun sendBroadCast(activity: Activity, intent: Intent?) {
        activity.sendBroadcast(intent)
    }

}