package com.example.wanandroid.viewmodel


import androidx.lifecycle.MutableLiveData
import com.example.wanandroid.entity.Article
import com.example.wanandroid.entity.Project
import com.example.wanandroid.viewmodel.baseviewmodel.BaseViewModel

class ProjectViewModel() : BaseViewModel(){
    var cid = MutableLiveData<Int>(294)
    val navList = MutableLiveData<List<Project>>()
    val projectList = MutableLiveData<List<Article>>()


}