package com.example.wanandroid.fragment

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
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
    lateinit var cidChangeReceiver: CidBroadcastReceiver
    private  val navList=ArrayList<Project>()
    private var projectList=ArrayList<Article>()
    val startURL:String="https://www.wanandroid.com/project/list/1/json?cid="
    var currentCid:String ="294"

    inner class CidBroadcastReceiver:BroadcastReceiver(){
        override fun onReceive(context: Context?, intent: Intent?) {
            //收到广播后处理：1.设置cid
            //2.list重置，获取新数据
            //3.数据加载
            Log.d("Broadcast","received")
            if (intent != null) {
                Log.d("intent","not null")
//                1.获得cid
                currentCid= intent.getStringExtra("data").toString()
//                2.获得内容，放入list
                projectList=getContent(startURL+currentCid)
//                3.数据加载 、展示
                val layoutManager = LinearLayoutManager(activity)
                content.layoutManager = layoutManager
                val adapter = ProjectContentAdapter(projectList)
                content.adapter = adapter
            }
        }
    }



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_project_list, container, false)
    }



    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val intentFilter = IntentFilter()
        intentFilter.addAction("project_cid")
        cidChangeReceiver = CidBroadcastReceiver()
        activity?.registerReceiver(cidChangeReceiver, intentFilter)
        //初始化导航内容
        initNav()
        //初始化首个导航的文章
        initContent(startURL+currentCid)
        // 设置文章之间的分割线
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

    override fun onDestroy() {
        super.onDestroy()
        //注销 接收器
        activity?.unregisterReceiver(cidChangeReceiver)
    }

    private fun initContent(url :String) {
        //data
        projectList=getContent("https://www.wanandroid.com/project/list/1/json?cid=294")
        //layout load
        val layoutManager = LinearLayoutManager(activity)
        content.layoutManager = layoutManager
        val adapter = ProjectContentAdapter(projectList)
        content.adapter = adapter
    }
    private fun refreshProjects() { //修改adapter == 修改list的内容==  通过用户点击的cid 修改list内容
        val intent = activity?.intent
        val cid: String? = intent?.getStringExtra("data")
        projectList.clear()


    }

//    }
//    private fun refreshProjects(adapter: ProjectContentAdapter) { //修改adapter == 修改list的内容==  通过用户点击的cid 修改list内容
//        val intent = activity?.intent
//
//        val url: String? = intent?.getStringExtra("data");
//        thread {
//            Thread.sleep(2000)
//            activity?.runOnUiThread {
////                initContent()
//                adapter.notifyDataSetChanged()
////                swipeRefresh.isRefreshing = false
//            }
//        }
//    }

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