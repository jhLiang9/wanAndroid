package com.example.wanandroid.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView.Adapter
import com.example.wanandroid.adapter.viewholder.BaseViewHolder

abstract class BaseAdapter<T> : Adapter<BaseViewHolder<T>>() {
    var dataList = ArrayList<T>()
    abstract override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): BaseViewHolder<T>


    override fun getItemCount(): Int = dataList.size

    override fun onBindViewHolder(
        holder: BaseViewHolder<T>,
        position: Int
    ) {
        holder.onBindViewHolder(dataList[position])
    }

    fun resetDataList() {
        dataList = ArrayList<T>()
    }


}