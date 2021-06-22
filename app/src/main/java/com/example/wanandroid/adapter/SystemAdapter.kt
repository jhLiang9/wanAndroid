package com.example.wanandroid.adapter

import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.wanandroid.R
import com.example.wanandroid.entity.System
import com.google.android.flexbox.FlexboxLayout

class SystemAdapter( val systemList:List<String>) : RecyclerView.Adapter<SystemAdapter.ViewHolder>() {
    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val system_item:TextView =view.findViewById(R.id.system_item)
//        val system_detail:RecyclerView=view.findViewById(R.id.system_detail)
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SystemAdapter.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_system, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val content = systemList[position]
        holder.system_item.text= content

    }

    override fun getItemCount(): Int {
        return systemList.size
    }
}