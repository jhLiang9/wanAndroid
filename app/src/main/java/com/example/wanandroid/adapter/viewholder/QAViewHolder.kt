package com.example.wanandroid.adapter.viewholder

import android.content.Intent
import com.example.wanandroid.activity.WebViewActivity
import com.example.wanandroid.databinding.ItemQaBinding
import com.example.wanandroid.entity.Article

class QAViewHolder(binding: ItemQaBinding) :
    BaseBindingViewHolder<Article, ItemQaBinding>(binding) {

    override fun onBindViewHolder(data: Article?) {
        this.data = data ?: return
        binding.title.text = data.title
        binding.author.text = data.author
        binding.time.text = data.niceDate
        binding.description.text = data.desc
        itemView.setOnClickListener {
            val url = data.link
            val intent = Intent(context, WebViewActivity::class.java)
            //打开WebView
            intent.putExtra("data", url);
            context.startActivity(intent)
        }
    }
}
