package com.example.wanandroid.utils

import org.greenrobot.eventbus.EventBus

/** 
 *  EventBus的工具类
 */
object EventBusUtil {

    fun post(event: Any) = EventBus.getDefault().post(event)
    fun postSticky(event: Any) = EventBus.getDefault().postSticky(event)
    fun register(obj: Any) = EventBus.getDefault().register(obj)
    fun unregister(obj: Any) = EventBus.getDefault().unregister(obj)

}