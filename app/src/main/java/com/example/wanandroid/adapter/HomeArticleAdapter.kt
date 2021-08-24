package com.example.wanandroid.adapter

import android.content.Context
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
import com.example.wanandroid.entity.Article
import com.example.wanandroid.fragment.HomePageFragment
import com.example.wanandroid.viewmodel.HomePageViewModel


class HomeArticleAdapter(private val articleList:List<Article>, val viewModel :HomePageViewModel) :RecyclerView.Adapter<HomeArticleAdapter.ViewHolder>(){

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val title: TextView = view.findViewById(R.id.title)
        val author: TextView = view.findViewById(R.id.author)
        val time:TextView = view.findViewById(R.id.time)
        val superChapterName: TextView =view.findViewById(R.id.superChapterName)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.article_item, parent, false)
        val viewHolder=ViewHolder(view)

        viewHolder.itemView.setOnClickListener {
            val position = viewHolder.bindingAdapterPosition //获取用户点击的position
            val article =articleList[position]
            val link=article.link
            val intent = Intent(parent.context, WebViewActivity::class.java)
            intent.putExtra("data", link);
            parent.context.startActivity(intent)
        }
        return viewHolder

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val article = articleList[position]
        holder.title.text= article.title
        holder.author.text=article.author
        holder.superChapterName.text=article.superChapterName
        holder.time.text=article.niceDate
        //加载下一页
        if(position == itemCount-5){
            getNextPage()

        }
    }
    private fun getNextPage() = viewModel.getArticlesByPage(viewModel.nextPage++)

    override fun getItemCount(): Int = articleList.size

}