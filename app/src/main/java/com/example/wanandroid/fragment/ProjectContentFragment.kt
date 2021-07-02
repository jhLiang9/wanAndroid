package com.example.wanandroid.fragment

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.wanandroid.R
import com.example.wanandroid.adapter.ProjectContentAdapter
import com.example.wanandroid.entity.Article
import com.example.wanandroid.viewmodel.SharedViewModel

import kotlinx.android.synthetic.main.fragment_project_content.*

import okhttp3.OkHttpClient
import okhttp3.Request
import org.json.JSONArray
import org.json.JSONObject
import kotlin.concurrent.thread



class ProjectContentFragment: Fragment() {
    private val model: SharedViewModel by activityViewModels()
    private var cid: Int? = null

    companion object {
        fun newInstance() = ProjectContentFragment()
    }


    var projectList=ArrayList<Article>()
    val startURL:String="https://www.wanandroid.com/project/list/1/json?cid="

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        //初始化首个导航的文章

        initContent()
        content.addItemDecoration(DividerItemDecoration(activity, DividerItemDecoration.VERTICAL))
        Thread.sleep(1000)

        //TODO: 处理加载数据的问题

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_project_content, container, false)
    }




    private fun initContent() {
        //data
        projectList=getContent("https://www.wanandroid.com/project/list/1/json?cid=294")

        //layout load
        content.addItemDecoration(DividerItemDecoration(activity,DividerItemDecoration.VERTICAL))
        val layoutManager = LinearLayoutManager(activity)
        content.layoutManager = layoutManager
        val adapter = ProjectContentAdapter(projectList)
        content.adapter = adapter
    }
     fun refreshProjects(cid:Int) { //修改adapter == 修改list的内容==  通过用户点击的cid 修改list内容
         projectList=getContent(startURL+cid.toString())
         val layoutManager = LinearLayoutManager(activity)
         content.layoutManager = layoutManager
         content.adapter?.notifyDataSetChanged()
    }


    private fun getContent(url:String):ArrayList<Article>{
        val res=ArrayList<Article>()
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
                    res.add(Article(title,author,time,description,link,""))
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
        return res
    }

}