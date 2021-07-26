package com.example.wanandroid.fragment

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.room.Room
import com.example.wanandroid.R
import com.example.wanandroid.adapter.ProjectContentAdapter
import com.example.wanandroid.database.ArticleDatabase
import com.example.wanandroid.databinding.FragmentProjectContentBinding
import com.example.wanandroid.entity.Article
import com.example.wanandroid.viewmodel.ProjectViewModel


import okhttp3.OkHttpClient
import okhttp3.Request
import org.json.JSONArray
import org.json.JSONObject
import kotlin.concurrent.thread



class ProjectContentFragment: Fragment() {

    private val viewModel :ProjectViewModel by activityViewModels()
    private lateinit var binding:FragmentProjectContentBinding
    private lateinit var adapter :ProjectContentAdapter
    val layoutManager = LinearLayoutManager(activity)

    var projectList=ArrayList<Article>()
    private val startURL:String="https://www.wanandroid.com/project/list/1/json?cid="


    companion object{
        @Volatile
        private var INSTANCE: ProjectContentFragment? = null
    }

    fun getInstance(context: Context):ProjectContentFragment{
        synchronized(this){
            var instance = INSTANCE
            if(instance==null){
                instance= ProjectContentFragment()
                INSTANCE =instance
            }
            return instance
        }
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)



    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_project_content, container, false)
        //初始化首个导航的文章
        //add layout
        binding.content.addItemDecoration(DividerItemDecoration(activity, DividerItemDecoration.VERTICAL))

        //data
        projectList=getContent("https://www.wanandroid.com/project/list/1/json?cid=294")

        //layout load
        binding.content.addItemDecoration(DividerItemDecoration(activity,DividerItemDecoration.VERTICAL))

        binding.content.layoutManager = layoutManager
        adapter = ProjectContentAdapter(projectList)
        binding.content.adapter = adapter


        viewModel.change.observe(viewLifecycleOwner, Observer{
                cidChanged-> if(cidChanged){
            Log.i("ProjectContent","get Cid changed")
            Log.i("ProjectContent",startURL+viewModel.cid.value.toString())
            projectList.removeAll(projectList)
//            projectList=getContent(startURL+viewModel.cid.value.toString())
            var fullUrl = startURL+viewModel.cid.value.toString()
            var temp :ArrayList<Article> = getContent(fullUrl)

            Log.i("Project", temp.size.toString())
            projectList.addAll(temp)
            binding.content.adapter?.notifyDataSetChanged()
            viewModel.setChange(false)

         }
        })

        return binding.root
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
                    val id =jsonObject.getInt("id")
                    res.add(Article(id,title,author,time,superChapterName = "",description = description,url = link))
                }

            } catch (e: Exception) {
                e.printStackTrace()
            }

        }.join()
//        projectList.removeAll(projectList)
//        projectList.addAll(res)
//        this.adapter.notifyDataSetChanged()
        return res
    }

}