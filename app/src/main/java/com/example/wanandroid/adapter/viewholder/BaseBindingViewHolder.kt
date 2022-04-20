package com.example.wanandroid.adapter.viewholder

import android.view.View
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView

abstract class BaseBindingViewHolder<T, DataBinding : ViewDataBinding>(val binding: DataBinding) :
    BaseViewHolder<T>(binding.root)