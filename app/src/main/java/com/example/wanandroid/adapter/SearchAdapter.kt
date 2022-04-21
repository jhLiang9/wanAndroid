package com.example.wanandroid.adapter


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.example.wanandroid.R
import com.example.wanandroid.adapter.viewholder.ArticleViewHolder
import com.example.wanandroid.adapter.viewholder.BaseViewHolder
import com.example.wanandroid.entity.Article


class SearchAdapter : BaseAdapter<Article>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArticleViewHolder {

        return ArticleViewHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.item_article,
                parent,
                false
            )
        )

    }

    override fun onBindViewHolder(holder: BaseViewHolder<Article>, position: Int) {
        super.onBindViewHolder(holder, position)
        if (position == itemCount - 5) {
            //todo 加载下一页
        }
    }


    override fun getItemCount(): Int = dataList.size

}