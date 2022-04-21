package com.example.wanandroid.adapter.viewholder

import com.example.wanandroid.activity.WebViewActivity
import com.example.wanandroid.databinding.ItemArticleBinding
import com.example.wanandroid.entity.Article

class ArticleViewHolder(binding: ItemArticleBinding) :
    BaseBindingViewHolder<Article, ItemArticleBinding>(binding) {

    override fun onBindViewHolder(data: Article?) {
        this.data = data ?: return
        binding.title.text = data.title
        binding.author.text = data.author
        binding.superChapterName.text = data.superChapterName
        binding.time.text = data.niceDate
        itemView.setOnClickListener {
            WebViewActivity.start(context, data.link)
        }
    }

}