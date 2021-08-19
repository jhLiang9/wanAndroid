package com.example.wanandroid.fragment.basefragment

import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class BaseFragment: Fragment() {
    fun <T: ViewModel> getViewModel(modelClass: Class<T>):T = ViewModelProvider(this).get(modelClass)
}