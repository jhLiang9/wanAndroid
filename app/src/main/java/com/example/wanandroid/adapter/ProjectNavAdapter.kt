package com.example.wanandroid.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.wanandroid.R
import com.example.wanandroid.activity.WebViewActivity

import com.example.wanandroid.entity.Project

/////val children: Project?, val courseId:Int, val id:Int, val name:String,
//                 val order:Int, val parentChapterID:Int, val userControlSetTop:Boolean, val visible:Int) {
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
            TODO("6.23")
            val intent = Intent(parent.context, WebViewActivity::class.java)
            intent.putExtra("data", nav.toString());
            parent.context.startActivity(intent)

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