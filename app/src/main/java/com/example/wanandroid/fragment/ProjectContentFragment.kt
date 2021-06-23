package com.example.wanandroid.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.wanandroid.R
import com.example.wanandroid.adapter.ProjectContentAdapter
import com.example.wanandroid.entity.Article
import com.example.wanandroid.entity.Project
import kotlinx.android.synthetic.main.fragment_project_list.*
import okhttp3.OkHttpClient
import okhttp3.Request
import org.json.JSONArray
import org.json.JSONObject
import kotlin.concurrent.thread


class ProjectContentFragment: Fragment() {
    var projectList=ArrayList<Project>()

    //有别的实现方法
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
//        initNav()
//        initContent(startURL)
//        val adapter = ProjectContentAdapter(projectList)
//        swipeRefresh.setColorSchemeResources(R.color.white)
//        swipeRefresh.setOnRefreshListener {
//            refreshProjects(adapter)
//        }


        content.addItemDecoration(DividerItemDecoration(activity, DividerItemDecoration.VERTICAL))
        Thread.sleep(1000)
        //TODO: 处理加载数据的问题

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_project_list, container, false)
    }

    private fun initContent(url :String) {
//data
        thread {
            val client= OkHttpClient()
            val request = Request.Builder()
                .url(url)
                //.url("https://www.wanandroid.com/article/list/0/json")
                .build()
            val response = client.newCall(request).execute()
            val responseData = response.body?.string()
            val jsondata= JSONObject(responseData).getString("data")
            val datas= JSONObject(jsondata).getString("datas")
            try {
                val jsonArray = JSONArray(datas)
                for (i in 0 until jsonArray.length()) {
                    val jsonObject = jsonArray.getJSONObject(i)
                    val title= jsonObject.getString("title")
                    val description= jsonObject.getString("desc")
                    val author = jsonObject.getString("author")
                    val time= jsonObject.getString("niceDate")
                    val link =jsonObject.getString("link")
//                    projectList.add(Article(title,author,time,description,link))
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }

        //layout load
        val layoutManager = LinearLayoutManager(activity)
        content.layoutManager = layoutManager
//        val adapter = ProjectContentAdapter(projectList)
//        content.adapter = adapter
    }

    private fun refreshProjects(adapter: ProjectContentAdapter) { //修改adapter == 修改list的内容==  通过用户点击的cid 修改list内容
        adapter.contentList
        thread {
            Thread.sleep(2000)
            activity?.runOnUiThread {
                adapter.notifyDataSetChanged()
//                swipeRefresh.isRefreshing = false
            }
        }
    }

}