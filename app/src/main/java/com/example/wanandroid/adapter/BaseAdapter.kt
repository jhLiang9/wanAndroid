package com.example.wanandroid.adapter

import android.annotation.SuppressLint
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView.Adapter
import com.example.wanandroid.adapter.viewholder.BaseViewHolder

@SuppressLint("NotifyDataSetChanged")
abstract class BaseAdapter<T> : Adapter<BaseViewHolder<T>>() {
    var dataList = ArrayList<T>()
    abstract override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<T>


    override fun getItemCount(): Int = dataList.size

    override fun onBindViewHolder(holder: BaseViewHolder<T>, position: Int) {
        holder.onBindViewHolder(dataList[position])
    }

    fun appendData(list: ArrayList<T>?) {
        list ?: return
        dataList.addAll(list)
        notifyDataSetChanged()
    }


    fun resetDataList(newDataList: ArrayList<T>) {
        dataList = newDataList
        notifyDataSetChanged()
    }

    fun clearData() {
        dataList.clear()
        notifyDataSetChanged()
    }

}