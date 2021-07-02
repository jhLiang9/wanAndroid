package com.example.wanandroid.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.View
import android.widget.TextView

import androidx.recyclerview.widget.RecyclerView
import com.example.wanandroid.R
import com.example.wanandroid.entity.System

class SystemContentAdapter ( val contentList:List<System>) : RecyclerView.Adapter<SystemContentAdapter.ViewHolder>() {
    inner class ViewHolder (view: View) : RecyclerView.ViewHolder(view){
        val system_content: TextView =view.findViewById(R.id.system_content)

    }
     override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SystemContentAdapter.ViewHolder {
         val view = LayoutInflater.from(parent.context).inflate(R.layout.item_system_content, parent, false)
         return ViewHolder(view)
     }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val content = contentList[position]
        holder.system_content.text= content.name
     }

     override fun getItemCount(): Int {
         return contentList.size
     }

 }