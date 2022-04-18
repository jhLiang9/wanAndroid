package com.example.wanandroid.adapter

import android.content.Intent
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Adapter
import com.example.wanandroid.activity.WebViewActivity

open class BaseAdapter<T> : Adapter<RecyclerView.ViewHolder>() {
    protected val dataList = ArrayList<T>()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        TODO("Not yet implemented")
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        holder.itemView.setOnClickListener {

        }
    }


    override fun getItemCount(): Int {
        return dataList.size
    }

}