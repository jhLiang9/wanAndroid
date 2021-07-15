package com.example.wanandroid.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.wanandroid.R
import com.example.wanandroid.adapter.HomeArticleAdapter
import com.example.wanandroid.databinding.FragmentHomePageBinding
import com.example.wanandroid.entity.Article
import com.example.wanandroid.utils.HtmlElementUtil

import okhttp3.OkHttpClient
import okhttp3.Request
import org.json.JSONArray
import org.json.JSONObject
import kotlin.concurrent.thread


class HomePageFragment : Fragment() {

    private  val articleList=ArrayList<Article>()
    private lateinit var binding:FragmentHomePageBinding
    companion object {
        @JvmStatic
        fun newInstance() = HomePageFragment().apply {
                arguments = Bundle().apply {

                }
            }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        arguments?.let {
//            param1 = it.getString(ARG_PARAM1)
//            param2 = it.getString(ARG_PARAM2)
//        }
    }

    override fun onDestroy() {

        super.onDestroy()
        Log.d("HomeDestory?:", "onDestroyView")
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_home_page,container,false)
        val articleRecyclerView = binding.ArticleRecyclerView

        initArticles()
        articleRecyclerView.addItemDecoration(DividerItemDecoration(activity, DividerItemDecoration.VERTICAL))

        Thread.sleep(1000)
        //TODO: 处理加载数据的问题
        val layoutManager = LinearLayoutManager(activity)
        articleRecyclerView.layoutManager = layoutManager
        val adapter = HomeArticleAdapter(articleList)
        articleRecyclerView.adapter = adapter

        return inflater.inflate(R.layout.fragment_home_page,container,false)
    }


    private fun initArticles() {//加载数据
        thread {
            val client= OkHttpClient()
            val request = Request.Builder()
                .url("https://www.wanandroid.com/article/list/0/json")
                .build()
            val response = client.newCall(request).execute()
            val responseData = response.body?.string()
            val jsondata= JSONObject(responseData).getString("data")
            val datas= JSONObject(jsondata).getString("datas")
            try {
                val jsonArray = JSONArray(datas)
                for (i in 0 until jsonArray.length()) {
                    val jsonObject = jsonArray.getJSONObject(i)
                    var title = HtmlElementUtil.removeHTMLTag(jsonObject.getString("title"))
                    if(title==""){
                        title=jsonObject.getString("title")
                    }
                    val time = jsonObject.getString("niceDate")
                    var author = jsonObject.getString("author")
                    if(author==""){
                        author=jsonObject.getString("shareUser")
                    }
                    val classify=jsonObject.getString("superChapterName")
                    val url=jsonObject.getString("link")
                    articleList.add(Article(title, author, time, classify,url,""))
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }

    }



}