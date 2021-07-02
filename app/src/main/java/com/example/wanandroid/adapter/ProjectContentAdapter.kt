package com.example.wanandroid.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.wanandroid.R
import com.example.wanandroid.activity.WebViewActivity
import com.example.wanandroid.entity.Article

class ProjectContentAdapter (val contentList:List<Article>) : RecyclerView.Adapter<ProjectContentAdapter.ViewHolder>() {

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val description :TextView=view.findViewById(R.id.description)
        val author:TextView =view.findViewById(R.id.author)
        val time :TextView =view.findViewById(R.id.time)
        val title :TextView =view.findViewById(R.id.title)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_project_content, parent, false)
        val viewHolder:ViewHolder =ViewHolder(view)

        viewHolder.itemView.setOnClickListener {
            val position = viewHolder.adapterPosition //获取用户点击的postion
            val article =contentList[position]
            val url=article.url
            val intent = Intent(parent.context, WebViewActivity::class.java)
            intent.putExtra("data", url);
            parent.context.startActivity(intent)
        }
        return viewHolder
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val content = contentList[position]
        holder.description.text=content.classify
        holder.author.text=content.author
        holder.time.text=content.time
        holder.title.text=content.title
    }

    override fun getItemCount(): Int {
        return contentList.size
    }
}