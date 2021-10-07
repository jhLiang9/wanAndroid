package com.example.wanandroid.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.wanandroid.R
import com.example.wanandroid.activity.WebViewActivity
import com.example.wanandroid.adapter.viewholder.BaseViewHolder
import com.example.wanandroid.entity.Article
import com.example.wanandroid.viewmodel.QAViewModel

class QAAdapter(private val qaList: List<Article>, val viewModel: QAViewModel) :
    RecyclerView.Adapter<QAAdapter.ViewHolder>() {

    inner class ViewHolder(itemView: View) : BaseViewHolder(itemView) {
            val title: TextView = itemView.findViewById(R.id.title)
            val author: TextView = itemView.findViewById(R.id.author)
            val time: TextView = itemView.findViewById(R.id.time)
            val description: TextView = itemView.findViewById(R.id.description)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): QAAdapter.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_qa, parent, false)
        val viewHolder = ViewHolder(view)
        viewHolder.itemView.setOnClickListener {
            val position = viewHolder.bindingAdapterPosition //获取用户点击的position
            val article = qaList[position]
            val url = article.link
            val intent = Intent(parent.context, WebViewActivity::class.java)
            //打开WebView
            intent.putExtra("data", url);
            parent.context.startActivity(intent)
        }
        return viewHolder
    }

    override fun onBindViewHolder(holder: QAAdapter.ViewHolder, position: Int) {
        val article = qaList[position]
        holder.title.text = article.title
        holder.author.text = article.author
        holder.time.text = article.niceDate
        holder.description.text = article.desc
        if (position >= itemCount - 5) {
            if (viewModel.hasNextPage()) {
                viewModel.getNextPage()
            }
        }
    }

    override fun getItemCount(): Int = qaList.size

}