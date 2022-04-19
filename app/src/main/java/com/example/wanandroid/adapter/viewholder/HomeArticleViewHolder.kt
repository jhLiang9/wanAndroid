package com.example.wanandroid.adapter.viewholder

import android.content.Context
import androidx.recyclerview.widget.RecyclerView
import com.example.wanandroid.activity.WebViewActivity
import com.example.wanandroid.databinding.ItemArticleBinding
import com.example.wanandroid.entity.Article

class HomeArticleViewHolder(val binding: ItemArticleBinding, val context: Context) :
    RecyclerView.ViewHolder(binding.root) {

    fun onBindViewHolder(article: Article) {
        binding.title.text = article.title
        binding.author.text = article.author
        binding.superChapterName.text = article.superChapterName
        binding.time.text = article.niceDate
        itemView.setOnClickListener {
            WebViewActivity.start(context, article.link)
        }
    }

}