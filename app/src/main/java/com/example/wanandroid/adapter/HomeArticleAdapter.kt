package com.example.wanandroid.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.wanandroid.R
import com.example.wanandroid.entity.Article

class HomeArticleAdapter(val articleList:List<Article>) :RecyclerView.Adapter<HomeArticleAdapter.ViewHolder>(){
    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val title: TextView = view.findViewById(R.id.title)
        val author: TextView = view.findViewById(R.id.author)
        val time:TextView = view.findViewById(R.id.time)
        val classify: TextView =view.findViewById(R.id.classify)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.article_item, parent, false)
        return ViewHolder(view)

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val article = articleList[position]
        holder.title.text= article.title
        holder.author.text=article.author
        holder.classify.text=article.classify
        holder.time.text=article.time
        //holder.fruitName.text = fruit.name

    }

    override fun getItemCount(): Int {
        return articleList.size
    }
}