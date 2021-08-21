package com.example.wanandroid.fragment.vm

import android.os.Bundle
import com.example.wanandroid.fragment.basefragment.BaseFragment
import com.example.wanandroid.viewmodel.HomePageViewModel

open class HomePageFragmentVM :BaseFragment(){
    protected lateinit var viewModel: HomePageViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        viewModel = getViewModel(HomePageViewModel::class.java)
        super.onCreate(savedInstanceState)
    }

}