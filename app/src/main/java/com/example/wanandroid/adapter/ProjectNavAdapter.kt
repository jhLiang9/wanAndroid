package com.example.wanandroid.adapter


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
    var click =0
    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val name: TextView = view.findViewById(R.id.item_project_nav)
        val relativeLayout: RelativeLayout = view.findViewById(R.id.rl0)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_project_nav, parent, false)

        val viewHolder = ViewHolder(view)
//        viewHolder.itemView.setOnClickListener {
//            //设置自己 不同颜色
//            val nav = viewModel.leftList[viewHolder.bindingAdapterPosition]
//            //获得用户点击的cid
//            val cid: Int = nav.id
//            click =viewHolder.bindingAdapterPosition
//            viewModel.cid.postValue(cid)
//            it.isClickable = false
//            notifyDataSetChanged()
//        }

//        viewHolder.itemView.setOnClickListener {
//            //TODO 修改颜色,点击其他时撤回上一个.
////            it.setBackgroundColor(Color.BLACK)
//            val position = viewHolder.bindingAdapterPosition //获取用户点击的position
//            val nav = viewModel.leftList[position]
//            //获得用户点击的cid
//            val cid: Int = nav.id
//            viewModel.cid.postValue(cid)
//        }
        return viewHolder
    }

    //渲染
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val nav = viewModel.leftList[position]
        holder.name.text = nav.name


        holder.relativeLayout.setOnClickListener {
            //设置自己 不同颜色
            val nav = viewModel.leftList[position]
            //获得用户点击的cid
            val cid: Int = nav.id
            click =position
            viewModel.cid.postValue(cid)
            it.isClickable = false
            notifyDataSetChanged()
        }

        if(click==position){
            holder.relativeLayout.setBackgroundColor(Color.parseColor("#F5F5F5"))
            holder.name.setTextColor(Color.parseColor("#888888"))
        }else{
            //TODO 了解RecyclerView机制
            //加else 解决 recyclerView复用错乱问题
            holder.relativeLayout.setBackgroundColor(Color.parseColor("#696969"))
            holder.name.setTextColor(Color.parseColor("#FFFFFF"))
        }


    }

    override fun getItemCount(): Int = viewModel.leftList.size


}