package com.example.wanandroid.adapter

import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.example.wanandroid.R
import com.example.wanandroid.activity.WebViewActivity

import com.example.wanandroid.entity.Project
import com.example.wanandroid.fragment.ProjectContentFragment
import com.example.wanandroid.viewmodel.ProjectViewModel
import java.nio.channels.Selector



class ProjectNavAdapter (var viewModel:ProjectViewModel,var navList:List<Project>) : RecyclerView.Adapter<ProjectNavAdapter.ViewHolder>(){

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
            viewModel._cid.value=cid //Solution
            viewModel._change.value = true
            Log.i("ProjectNavAdapter","cid passed ${viewModel._cid.value}")
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