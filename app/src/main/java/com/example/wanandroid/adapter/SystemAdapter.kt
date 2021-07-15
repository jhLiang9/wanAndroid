package com.example.wanandroid.adapter

import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView

import androidx.recyclerview.widget.RecyclerView
import com.example.wanandroid.R
import com.example.wanandroid.entity.System


class SystemAdapter( val systemList:List<String>) : RecyclerView.Adapter<SystemAdapter.ViewHolder>() {
    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val systemItem:TextView =view.findViewById(R.id.systemTitle)
//        val image:ImageView = view.findViewById(R.id.imageView)

    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SystemAdapter.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_system_nav, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val title = systemList[position]
        holder.systemItem.text= title.toString()

    }

    override fun getItemCount(): Int {
        return systemList.size
    }
}