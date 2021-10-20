package com.example.wanandroid.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.recyclerview.widget.RecyclerView
import com.example.wanandroid.R
import com.example.wanandroid.activity.WebViewActivity
import com.example.wanandroid.entity.Article
import com.example.wanandroid.viewmodel.HomePageViewModel


class HomeArticleAdapter<T>(val viewModel: HomePageViewModel) :
    RecyclerView.Adapter<HomeArticleAdapter<T>.ViewHolder>() {
    private var data: ArrayList<T> = ArrayList()
    var loadMore = MutableLiveData<Boolean>(false)
    fun setData(data: ArrayList<T>) {
        this.data.addAll(data)
    }


    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val title: TextView = view.findViewById(R.id.title)
        val author: TextView = view.findViewById(R.id.author)
        val time: TextView = view.findViewById(R.id.time)
        val superChapterName: TextView = view.findViewById(R.id.superChapterName)
        
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_article, parent, false)
        val viewHolder = ViewHolder(view)


        viewHolder.itemView.setOnClickListener {
            val position = viewHolder.bindingAdapterPosition //获取用户点击的position
            val article = data[position] as Article
            val link = article.link
            val intent = Intent(parent.context, WebViewActivity::class.java)
            intent.putExtra("data", link);
            parent.context.startActivity(intent)
        }
        return viewHolder
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val article = data[position] as Article
        holder.title.text = article.title
        holder.author.text = article.author
        holder.superChapterName.text = article.superChapterName
        holder.time.text = article.niceDate
        if (position >= itemCount - 5) {
            getNextPage()
        }
    }

    private fun getNextPage() = viewModel.getNextPage()

    override fun getItemCount(): Int = data.size

}