package com.example.wanandroid.adapter


import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.wanandroid.R
import android.view.LayoutInflater
import androidx.recyclerview.widget.RecyclerView
import com.example.wanandroid.viewmodel.ProjectViewModel


class ProjectNavAdapter(var viewModel: ProjectViewModel) :
    RecyclerView.Adapter<ProjectNavAdapter.ViewHolder>() {

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val name: TextView = view.findViewById(R.id.item_project_nav)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_project_nav, parent, false)

        val viewHolder = ViewHolder(view)

        viewHolder.itemView.setOnClickListener {
            val position = viewHolder.bindingAdapterPosition //获取用户点击的position
            val nav = viewModel.leftList[position]
            //获得用户点击的cid
            val cid: Int = nav.id
            viewModel.cid.postValue(cid)
        }
        return viewHolder
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val nav = viewModel.leftList[position]
        holder.name.text = nav.name
    }

    override fun getItemCount(): Int = viewModel.leftList.size

}