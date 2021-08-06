package com.example.wanandroid.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.WorkerThread
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.wanandroid.R
import com.example.wanandroid.adapter.HomeArticleAdapter
import com.example.wanandroid.databinding.FragmentHomePageBinding
import com.example.wanandroid.entity.Article
import com.example.wanandroid.service.ArticleApi
import com.example.wanandroid.utils.HtmlElementUtil
import okhttp3.*

import okhttp3.internal.wait
import org.json.JSONArray
import org.json.JSONObject
import java.io.IOException
//import retrofit2.Call
//import retrofit2.Callback
//import retrofit2.Response
import kotlin.concurrent.thread


class HomePageFragment : Fragment() {

    //OkHttpClient
    private val client = OkHttpClient()

    private val articleList = ArrayList<Article>()
    private lateinit var binding: FragmentHomePageBinding

    companion object {
        private var instance: HomePageFragment? = null
        fun getInstance(): HomePageFragment {
            if (instance == null) {
                synchronized(this) {
                    instance = HomePageFragment()
                }
            }
            return instance as HomePageFragment
        }
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        Log.d("HomePage", "onActivityCreated")
    }


    override fun onDestroy() {
        super.onDestroy()
        Log.d("HomePage:", "onDestroyView")
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_home_page, container, false)

        binding.ArticleRecyclerView.addItemDecoration(
            DividerItemDecoration(
                activity,
                DividerItemDecoration.VERTICAL
            )
        )
        initFirstPage()

        val layoutManager = LinearLayoutManager(activity)
        binding.ArticleRecyclerView.layoutManager = layoutManager
        //有数据
        val adapter = HomeArticleAdapter(articleList)
        binding.ArticleRecyclerView.adapter = adapter

        return binding.root
    }


    private fun initFirstPage() = getArticlesByPage(0)

    @WorkerThread
    private fun getArticlesByPage(page: Int) {
        val url: String = "https://www.wanandroid.com/article/list/$page/json"
        val request = Request.Builder()
            .url(url)
            .build()
        val call: Call = client.newCall(request)
        call.enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                Log.e("OkHttp on fail", "getArticleByPage()")
                e.printStackTrace()
            }

            override fun onResponse(call: Call, response: Response) {
                val responseData = response.body?.string()
                val jsondata = JSONObject(responseData).getString("data")
                val datas = JSONObject(jsondata).getString("datas")
                val jsonArray = JSONArray(datas)
                for (i in 0 until jsonArray.length()) {
                    val jsonObject = jsonArray.getJSONObject(i)
                    var title = HtmlElementUtil.removeHTMLTag(jsonObject.getString("title"))
                    if (title == "") {
                        title = jsonObject.getString("title")
                    }
                    val time = jsonObject.getString("niceDate")
                    var author = jsonObject.getString("author")
                    if (author == "") {
                        author = jsonObject.getString("shareUser")
                    }
                    val superChapterName = jsonObject.getString("superChapterName")
                    val url = jsonObject.getString("link")
                    val id = jsonObject.getInt("id")
                    articleList.add(Article(id, title, author, time, superChapterName, url, ""))
                }
            }
        })
    }
}

