package com.example.wanandroid.adapter

import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.wanandroid.R
import com.example.wanandroid.activity.MainActivity
import com.example.wanandroid.activity.WebViewActivity
import com.example.wanandroid.entity.Article

class QAAdapter(val qaList:List<Article>) : RecyclerView.Adapter<QAAdapter.ViewHolder>() {

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val title: TextView = view.findViewById(R.id.title)
        val author: TextView = view.findViewById(R.id.author)
        val time:TextView = view.findViewById(R.id.time)
        val description:TextView=view.findViewById(R.id.description)
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): QAAdapter.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_qa, parent, false)
        val viewHolder:ViewHolder =ViewHolder(view)
        viewHolder.itemView.setOnClickListener {
            val position = viewHolder.bindingAdapterPosition //获取用户点击的position
            val article =qaList[position]
            val url=article.url
            val intent = Intent(parent.context, WebViewActivity::class.java)
            //打开WebView
            intent.putExtra("data", url);
            parent.context.startActivity(intent)
        }
        return viewHolder
    }

    override fun onBindViewHolder(holder: QAAdapter.ViewHolder, position: Int) {
        val article = qaList[position]
        holder.title.text= article.title
        holder.author.text=article.author
        holder.time.text=article.time
        holder.description.text=article.description
    }

    override fun getItemCount(): Int {
        return qaList.size
    }
}