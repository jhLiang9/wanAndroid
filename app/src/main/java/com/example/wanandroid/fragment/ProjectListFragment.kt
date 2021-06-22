package com.example.wanandroid.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.wanandroid.R
import com.example.wanandroid.adapter.ProjectContentAdapter
import com.example.wanandroid.adapter.ProjectNavAdapter
import com.example.wanandroid.entity.Article
import com.example.wanandroid.entity.Project
import kotlinx.android.synthetic.main.fragment_project_list.*
import okhttp3.OkHttpClient
import okhttp3.Request
import org.json.JSONArray
import org.json.JSONObject
import kotlin.concurrent.thread

class ProjectListFragment:Fragment() {

    private  val navList=ArrayList<Project>()
    private  val projectList=ArrayList<Article>()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_project_list, container, false)
    }
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        initNav()
        initContent()
        content.addItemDecoration(DividerItemDecoration(activity, DividerItemDecoration.VERTICAL))

        Thread.sleep(1000)
        //TODO: 处理加载数据的问题

    }



    private fun initNav() {
        //layout

        //分割线
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
                    val name=jsonObject.getString("name")

                    navList.add(Project(null,null,null,name,null,null,null
                    ,null))
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }

        //layout
        val layoutManager = LinearLayoutManager(activity)
        nav.layoutManager = layoutManager
        val adapter = ProjectNavAdapter(navList)
        nav.adapter = adapter
    }

    private fun initContent() {
//data
        thread {
            val client= OkHttpClient()
            val request = Request.Builder()
                .url("https://www.wanandroid.com/project/list/1/json?cid=294")
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
                    projectList.add(Article(title,author,time,description,link))
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }

        //layout load
        val layoutManager = LinearLayoutManager(activity)
        content.layoutManager = layoutManager
        val adapter = ProjectContentAdapter(projectList)
        content.adapter = adapter
    }


}