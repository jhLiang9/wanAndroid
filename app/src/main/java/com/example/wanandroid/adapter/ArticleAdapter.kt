package com.example.wanandroid.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.MutableLiveData
import com.example.wanandroid.R
import com.example.wanandroid.adapter.viewholder.ArticleViewHolder
import com.example.wanandroid.adapter.viewholder.BaseViewHolder
import com.example.wanandroid.entity.Article
import com.example.wanandroid.viewmodel.HomePageViewModel


class ArticleAdapter(val viewModel: HomePageViewModel) :
    BaseAdapter<Article>() {

    var loadMore: MutableLiveData<Boolean> = MutableLiveData()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArticleViewHolder =
        ArticleViewHolder(DataBindingUtil.inflate(LayoutInflater.from(parent.context), R.layout.item_article, parent, false))

    override fun onBindViewHolder(holder: BaseViewHolder<Article>, position: Int) {
        holder.onBindViewHolder(dataList[position])
        if (position >= itemCount - 5) {
            getNextPage()
        }
    }

    private fun getNextPage() = viewModel.getNextPage()

}