package com.example.wanandroid.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.example.wanandroid.R
import com.example.wanandroid.adapter.viewholder.BaseViewHolder
import com.example.wanandroid.adapter.viewholder.QAViewHolder
import com.example.wanandroid.entity.Article
import com.example.wanandroid.viewmodel.QAViewModel

class QAAdapter(val viewModel: QAViewModel) :
    BaseAdapter<Article>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): BaseViewHolder<Article> {
        return QAViewHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.item_qa,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: BaseViewHolder<Article>, position: Int) {
        super.onBindViewHolder(holder, position)

        if (position >= itemCount - 5) {
            if (viewModel.hasNextPage()) {
                viewModel.getNextPage()
            }
        }
    }

}