package com.example.wanandroid.fragment


import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment

import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.wanandroid.R
import com.example.wanandroid.adapter.ProjectNavAdapter
import com.example.wanandroid.databinding.FragmentProjectListBinding
import com.example.wanandroid.entity.Project
import com.example.wanandroid.viewmodel.ProjectViewModel

import okhttp3.OkHttpClient
import okhttp3.Request
import org.json.JSONArray
import org.json.JSONObject
import kotlin.concurrent.thread

class ProjectListFragment:Fragment() {

    private val navList=ArrayList<Project>()

    private lateinit var viewModel :ProjectViewModel

    private lateinit var binding:FragmentProjectListBinding

    companion object{
        //注解是为了兼容Java调用时习惯
        @JvmField
        var cid :Int= 294
        @JvmStatic
        fun getInstance(){

        }

    }



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View? {

        viewModel= ViewModelProvider(this).get(ProjectViewModel::class.java)
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_project_list,container,false)
//        binding.nav.adapter= adapter



        return inflater.inflate(R.layout.fragment_project_list, container, false)
    }



    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        Thread.sleep(1000)
        //TODO: 处理加载数据的问题
//        初始化导航内容
        initNav()


    }

    private fun initNav() {
        //layout
        //分割线
        val nav =binding.nav
        nav.addItemDecoration(DividerItemDecoration(activity,DividerItemDecoration.VERTICAL))
        //layout data
        thread {
            val client= OkHttpClient()
            val request = Request.Builder()
                .url("https://www.wanandroid.com/project/tree/json")
        //.url("https://www.wanandroid.com/article/list/0/json")
                .build()
            val response = client.newCall(request).execute()
            val responseData = response.body?.string()
            val jsondata= JSONObject(responseData).getString("data")
            try {
                val jsonArray = JSONArray(jsondata)
                for (i in 0 until jsonArray.length()) {
                    val jsonObject = jsonArray.getJSONObject(i)
                    val id=jsonObject.getInt("id")
                    val name=jsonObject.getString("name")
                    navList.add(Project(null,null,id,name,null,null,null
                    ,null))
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }

        //layout
//
//        val layoutManager = LinearLayoutManager(activity)
//        nav.layoutManager = layoutManager
//        val adapter = ProjectNavAdapter(navList)
//        nav.adapter = adapter
    }




}