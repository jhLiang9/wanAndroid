package com.example.wanandroid.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.RecyclerView
import com.example.wanandroid.R
import com.example.wanandroid.adapter.viewholder.HomeArticleViewHolder
import com.example.wanandroid.entity.Article
import com.example.wanandroid.viewmodel.HomePageViewModel


class HomeArticleAdapter(val viewModel: HomePageViewModel) :
    RecyclerView.Adapter<HomeArticleViewHolder>() {
    private var data: ArrayList<Article> = ArrayList()
    var loadMore = MutableLiveData(false)


    @SuppressLint("NotifyDataSetChanged")
    fun resetData(newData: ArrayList<Article>) {
        with(data) {
            clear()
            addAll(newData)
        }
        notifyDataSetChanged()
    }

    fun appendData(data: ArrayList<Article>) {
        this.data.addAll(data)
        notifyDataSetChanged()
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeArticleViewHolder =
        HomeArticleViewHolder(
            DataBindingUtil.inflate(LayoutInflater.from(parent.context), R.layout.item_article, parent, false)
        )


    override fun onBindViewHolder(holder: HomeArticleViewHolder, position: Int) {
        holder.onBindViewHolder(data[position])

        if (position >= itemCount - 5) {
            getNextPage()
        }
    }

    private fun getNextPage() = viewModel.getNextPage()

    override fun getItemCount(): Int = data.size

}