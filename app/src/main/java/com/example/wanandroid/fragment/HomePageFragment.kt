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

//TODO:Not work
class HomePageFragment : Fragment() {

    private  val articleList=ArrayList<Article>()
    private lateinit var binding:FragmentHomePageBinding

    val layoutManager = LinearLayoutManager(activity)

    private  var adapter = HomeArticleAdapter(articleList)


    companion object {
        @JvmStatic
        fun newInstance() = HomePageFragment().apply {
                arguments = Bundle().apply {

                }
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

        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_home_page,container,false)
        Log.d("HomePageFragment","onCreateView")

        //TODO: 处理加载数据的问题


        initArticles()
        binding.ArticleRecyclerView.layoutManager = layoutManager
        adapter = HomeArticleAdapter(articleList)
        binding.ArticleRecyclerView.adapter = adapter
        binding.ArticleRecyclerView.addItemDecoration(DividerItemDecoration(activity, DividerItemDecoration.VERTICAL))
        binding.ArticleRecyclerView.adapter?.notifyDataSetChanged()

        return binding.root
    }


    private fun initArticles() {//加载数据
        thread {
            val client= OkHttpClient()
            val request = Request.Builder()
                .url("https://www.wanandroid.com/article/list/0/json")
                .build()
            try {
                val response = client.newCall(request).execute()
                val responseData = response.body?.string()
                val jsondata= JSONObject(responseData).getString("data")
                val datas= JSONObject(jsondata).getString("datas")
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
                    val superChapterName=jsonObject.getString("superChapterName")
                    val url=jsonObject.getString("link")
                    val id=jsonObject.getInt("id")
                    articleList.add(Article(id,title, author, time, superChapterName,url,""))
                }
            } catch (e: Exception) {
                Log.d("HomePage","initArticles Exception")
                e.printStackTrace()
            }
        }
        binding.ArticleRecyclerView.adapter?.notifyDataSetChanged()
        Log.i("HomePage","Notify ")
    }



}