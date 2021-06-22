package com.example.wanandroid.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.wanandroid.R

import com.example.wanandroid.entity.Project

/////val children: Project?, val courseId:Int, val id:Int, val name:String,
//                 val order:Int, val parentChapterID:Int, val userControlSetTop:Boolean, val visible:Int) {
class ProjectNavAdapter (val navList:List<Project>) : RecyclerView.Adapter<ProjectNavAdapter.ViewHolder>(){
    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val name: TextView = view.findViewById(R.id.item_project_nav)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_project_nav, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val nav = navList[position]
        holder.name.text = nav.name

    }

    override fun getItemCount(): Int {
        return navList.size
    }

}