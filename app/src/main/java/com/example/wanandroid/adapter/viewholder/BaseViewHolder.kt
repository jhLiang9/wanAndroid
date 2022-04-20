package com.example.wanandroid.adapter.viewholder

import android.content.Context
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder

abstract class BaseViewHolder<T>(view: View) : ViewHolder(view) {
    var data: T? = null
    val context: Context = view.context
    abstract fun onBindViewHolder(data: T?)
}