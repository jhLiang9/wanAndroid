package com.example.wanandroid.adapter

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.view.children
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.RecyclerView
import com.example.wanandroid.R
import com.example.wanandroid.activity.MainActivity
import com.example.wanandroid.activity.WebViewActivity
import com.example.wanandroid.entity.Project
import com.example.wanandroid.fragment.ProjectContentFragment


class ProjectNavAdapter (val navList:List<Project>) : RecyclerView.Adapter<ProjectNavAdapter.ViewHolder>(){
    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val name: TextView = view.findViewById(R.id.item_project_nav)
    }



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_project_nav, parent, false)
        val viewHolder :ViewHolder=ViewHolder(view)
        viewHolder.itemView.setOnClickListener {
            val position = viewHolder.adapterPosition //获取用户点击的postion
            val nav =navList[position]
            //获得用户点击的cid
            val cid :Int= nav.id

            val fragment = ProjectContentFragment()
            fragment.refreshProjects(cid)

//            val intent = Intent(parent.context, MainActivity::class.java)
//            intent.putExtra("data",cid);
//            intent.setPackage(parent.context.packageName)
//            parent.context.sendBroadcast(intent)
//            intent.setAction("project_cid")
//            Log.d("Broadcast","sended")
//            parent.context.sendOrderedBroadcast(intent, null)
        }
        return viewHolder
    }




    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val nav = navList[position]
        holder.name.text = nav.name
    }

    override fun getItemCount(): Int {
        return navList.size
    }

}