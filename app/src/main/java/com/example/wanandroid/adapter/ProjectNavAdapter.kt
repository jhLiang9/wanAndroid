package com.example.wanandroid.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.wanandroid.R

import com.example.wanandroid.entity.Project
import com.example.wanandroid.fragment.ProjectContentFragment
import java.nio.channels.Selector



class ProjectNavAdapter (var navList:List<Project>) : RecyclerView.Adapter<ProjectNavAdapter.ViewHolder>(){

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val name: TextView = view.findViewById(R.id.item_project_nav)
    }

    private lateinit var itemSelector: Selector


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