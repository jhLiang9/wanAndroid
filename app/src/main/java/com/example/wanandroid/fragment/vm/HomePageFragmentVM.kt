package com.example.wanandroid.fragment.vm

import android.os.Bundle
import com.example.wanandroid.fragment.basefragment.BaseFragment
import com.example.wanandroid.viewmodel.HomePageViewModel

open class HomePageFragmentVM :BaseFragment(){
    protected val  viewModel by lazy{getViewModel(HomePageViewModel::class.java)}


}