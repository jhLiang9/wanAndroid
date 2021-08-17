package com.example.wanandroid.fragment

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.wanandroid.R
import com.example.wanandroid.adapter.ProjectContentAdapter
import com.example.wanandroid.databinding.FragmentProjectContentBinding
import com.example.wanandroid.entity.Article
import com.example.wanandroid.event.ProjectContentEvent
import com.example.wanandroid.event.refresh.ProjectRefreshEvent
import com.example.wanandroid.viewmodel.ProjectViewModel
import okhttp3.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode


import org.json.JSONArray
import org.json.JSONObject
import java.io.IOException


class ProjectContentFragment : Fragment() {

    private val viewModel: ProjectViewModel by activityViewModels()
    private lateinit var binding: FragmentProjectContentBinding
    private lateinit var adapter: ProjectContentAdapter
    private val client = OkHttpClient()

    private val layoutManager = LinearLayoutManager(activity)

    var projectList = ArrayList<Article<Any>>()
    private val startURL: String = "https://www.wanandroid.com/project/list/1/json?cid="


    companion object {
        @Volatile
        private var INSTANCE: ProjectContentFragment? = null
        fun getInstance(): ProjectContentFragment {
            synchronized(this) {
                var instance = INSTANCE
                if (instance == null) {
                    instance = ProjectContentFragment()
                    INSTANCE = instance
                }
                return instance
            }
        }
    }


    @SuppressLint("NotifyDataSetChanged")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        EventBus.getDefault().register(this)
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_project_content, container, false)
        initView()
        viewModel.cid.observe(viewLifecycleOwner, Observer { cid ->
                val fullUrl = startURL + cid.toString()
                getContent(fullUrl)
        })

        return binding.root
    }

    override fun onDestroy() {
        super.onDestroy()
        EventBus.getDefault().unregister(this)
    }

    @SuppressLint("NotifyDataSetChanged")
    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onEvent(projectContentEvent: ProjectContentEvent){
        binding.content.adapter?.notifyDataSetChanged()
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
        binding.content.addItemDecoration(DividerItemDecoration(activity, DividerItemDecoration.VERTICAL))
        binding.content.layoutManager = layoutManager
        adapter = ProjectContentAdapter(projectList)
        binding.content.adapter = adapter

    }
    private fun getContent(cid: Int,page:Int){
        getContent("https://www.wanandroid.com/project/list/$page/json?cid=$cid")
    }

    private fun getContent(url: String){
        val request = Request.Builder()
            .url(url)
            .build()
        val call: Call = client.newCall(request)
        call.enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
            }

            override fun onResponse(call: Call, response: Response) {
                projectList.clear()
                val responseData = response.body?.string()

                val errorCode = JSONObject(responseData).getString("errorCode")
                val errorMessage = JSONObject(responseData).getString("errorMsg")
                val jsondata = JSONObject(responseData).getString("data")
                val curPage= JSONObject(jsondata).getInt("curPage")
                val datas = JSONObject(jsondata).getString("datas")

                val jsonArray = JSONArray(datas)
                for (i in 0 until jsonArray.length()) {
                    val jsonObject = jsonArray.getJSONObject(i)
                    val title = jsonObject.getString("title")
                    val description = jsonObject.getString("desc")
                    val author = jsonObject.getString("author")
                    val time = jsonObject.getString("niceDate")
                    val link = jsonObject.getString("link")
                    val id = jsonObject.getInt("id")
//                    projectList.add(
//                        Article(id, title, author, time, superChapterName = "", description = description, url = link)
//                    )
                }
                val over =  JSONObject(jsondata).getBoolean("over")
                val pageCount = JSONObject(jsondata).getInt("pageCount")
                val size =JSONObject(jsondata).getInt("size")
                val total =JSONObject(jsondata).getInt("total")
                EventBus.getDefault().post(ProjectContentEvent())
            }
        })
    }
}
