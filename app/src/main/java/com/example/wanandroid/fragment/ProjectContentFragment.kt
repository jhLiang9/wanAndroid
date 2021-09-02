package com.example.wanandroid.fragment

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.wanandroid.R
import com.example.wanandroid.adapter.ProjectContentAdapter
import com.example.wanandroid.databinding.FragmentProjectContentBinding
import com.example.wanandroid.entity.Article
import com.example.wanandroid.entity.list.ArticleList
import com.example.wanandroid.event.ProjectContentEvent
import com.example.wanandroid.event.refresh.ProjectRefreshEvent
import com.example.wanandroid.viewmodel.ProjectViewModel
import com.google.gson.Gson
import okhttp3.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode


import org.json.JSONArray
import org.json.JSONObject
import java.io.IOException


class ProjectContentFragment : Fragment() {

    private val viewModel: ProjectViewModel by viewModels()
    private lateinit var binding: FragmentProjectContentBinding

    var projectList = ArrayList<Article>()


    @SuppressLint("NotifyDataSetChanged")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        EventBus.getDefault().register(this)
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_project_content, container, false)
        initView()
        viewModel.cid.observe(viewLifecycleOwner,  { cid ->
            getContent(cid,1)
        })
        viewModel.projectList.observe(viewLifecycleOwner,{
            projectList.clear()
            projectList.addAll(it.data.datas)
            binding.content.adapter?.notifyDataSetChanged()
            binding.loadingPanel.visibility=View.GONE
        })
        return binding.root
    }

    override fun onDestroy() {
        super.onDestroy()
        EventBus.getDefault().unregister(this)
    }


    @SuppressLint("NotifyDataSetChanged")
    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onEvent(event: ProjectRefreshEvent){
        refresh()
    }

    private fun refresh(){
        getContent(294,1)
    }

    /**
     * 初始化首个导航的文章
     */
    private fun initView() {
        //add layout
        binding.content.addItemDecoration(DividerItemDecoration(activity, DividerItemDecoration.VERTICAL))
        //data
        getContent(294,1)
        //layout load
        binding.content.layoutManager = LinearLayoutManager(activity)
        binding.content.adapter = ProjectContentAdapter(projectList)
    }

    private fun getContent(cid: Int,page:Int)  =viewModel.getProjectContent(cid,page)


}
