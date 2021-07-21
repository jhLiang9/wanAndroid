package com.example.wanandroid.adapter

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.wanandroid.R
import com.example.wanandroid.activity.WebViewActivity
import com.example.wanandroid.databinding.ArticleItemBinding
import com.example.wanandroid.entity.Article


class HomeArticleAdapter(val articleList:List<Article>) :RecyclerView.Adapter<HomeArticleAdapter.ViewHolder>(){

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val title: TextView = view.findViewById(R.id.title)
        val author: TextView = view.findViewById(R.id.author)
        val time:TextView = view.findViewById(R.id.time)
        val superChapterName: TextView =view.findViewById(R.id.superChapterName)
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

//        val binding = ArticleItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        val view = LayoutInflater.from(parent.context).inflate(R.layout.article_item, parent, false)
        val viewHolder=ViewHolder(view)

        viewHolder.itemView.setOnClickListener {
            val position = viewHolder.adapterPosition //获取用户点击的postion
            val article =articleList[position]
            val url=article.url
            val intent = Intent(parent.context, WebViewActivity::class.java)
            intent.putExtra("data", url);
            parent.context.startActivity(intent)
        }
        return viewHolder

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val article = articleList[position]
        holder.title.text= article.title
        holder.author.text=article.author
        holder.superChapterName.text=article.superChapterName
        holder.time.text=article.time


    }

    override fun getItemCount(): Int {
        return articleList.size
    }
}