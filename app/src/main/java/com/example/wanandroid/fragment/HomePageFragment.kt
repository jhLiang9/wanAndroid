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
import com.example.wanandroid.adapter.HomeArticleAdapter
import com.example.wanandroid.entity.Article
import com.example.wanandroid.utils.HtmlElementUtil
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_home_page.*
import kotlinx.android.synthetic.main.fragment_home_page.ArticleRecyclerView
import kotlinx.android.synthetic.main.fragment_project_list.*

import okhttp3.OkHttpClient
import okhttp3.Request
import org.json.JSONArray
import org.json.JSONObject
import kotlin.concurrent.thread

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER

/**
 * A simple [Fragment] subclass.
 * Use the [HomePageFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class HomePageFragment : Fragment() {

    private  val articleList=ArrayList<Article>()

    companion object {
        @JvmStatic
        fun newInstance() = HomePageFragment().apply {
                arguments = Bundle().apply {

                }
            }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initArticles()
        ArticleRecyclerView.addItemDecoration(DividerItemDecoration(activity, DividerItemDecoration.VERTICAL))

        Thread.sleep(1000)
        //TODO: 处理加载数据的问题
        val layoutManager = LinearLayoutManager(activity)
        ArticleRecyclerView.layoutManager = layoutManager
        val adapter = HomeArticleAdapter(articleList)
        ArticleRecyclerView.adapter = adapter
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
        // Inflate the layout for this fragment

        //https://blog.csdn.net/zhuchenglin830/article/details/82286109  Fragment中RecyclerView的使用解析


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