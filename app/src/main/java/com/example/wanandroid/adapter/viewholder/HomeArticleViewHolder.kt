package com.example.wanandroid.adapter.viewholder

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.wanandroid.R

class HomeArticleViewHolder(view: View): RecyclerView.ViewHolder(view) {
        val title: TextView = view.findViewById(R.id.title)
        val author: TextView = view.findViewById(R.id.author)
        val time: TextView = view.findViewById(R.id.time)
        val superChapterName: TextView = view.findViewById(R.id.superChapterName)


}