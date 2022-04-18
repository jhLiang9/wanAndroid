package com.example.wanandroid.adapter


import android.annotation.SuppressLint
import android.graphics.Color
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.wanandroid.R
import android.view.LayoutInflater
import android.widget.RelativeLayout
import androidx.recyclerview.widget.RecyclerView
import com.example.wanandroid.viewmodel.ProjectViewModel


class ProjectNavAdapter(var viewModel: ProjectViewModel) :
    RecyclerView.Adapter<ProjectNavAdapter.ViewHolder>() {
    var click = 0

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val name: TextView = view.findViewById(R.id.item_project_nav)
        val relativeLayout: RelativeLayout = view.findViewById(R.id.rl0)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_project_nav, parent, false)

        return ViewHolder(view)
    }

    //渲染
    @SuppressLint("NotifyDataSetChanged")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val nav = viewModel.leftList[position]
        holder.name.text = nav.name


        holder.relativeLayout.setOnClickListener {
            //获得用户点击的cid
            val cid: Int = viewModel.leftList[position].id
            click = position
            viewModel.cid.postValue(cid)
            it.isClickable = false
            notifyDataSetChanged()
        }

        if (click == position) {
            holder.relativeLayout.setBackgroundColor(Color.parseColor("#007FFF"))
            holder.name.setTextColor(Color.parseColor("#FFFFFF"))
        } else {
            holder.relativeLayout.setBackgroundColor(Color.parseColor("#C0C0C0"))
            holder.name.setTextColor(Color.parseColor("#FFFFFF"))
        }


    }

    override fun getItemCount(): Int = viewModel.leftList.size


}