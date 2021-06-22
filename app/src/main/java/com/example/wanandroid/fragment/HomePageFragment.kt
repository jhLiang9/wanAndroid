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

    private var param1: String? = null
    private var param2: String? = null

    private  val articleList=ArrayList<Article>()


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
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
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

//        initArticles() // 初始化文章数据
//        val layoutManager = LinearLayoutManager(this.context)
//        ArticleRecyclerView.layoutManager = layoutManager
//        ArticleRecyclerView.adapter=HomeArticleAdapter(articleList)
//        test.setOnClickListener {
//            Toast.makeText(activity,"Clicked",Toast.LENGTH_SHORT).show()
//        }



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

                    val title = jsonObject.getString("title")
                    val time = jsonObject.getString("niceDate")
                    var author = jsonObject.getString("author")
                    if(author==""){
                        author=jsonObject.getString("shareUser")
                    }
                    val classify=jsonObject.getString("superChapterName")
                    val url=jsonObject.getString("link")

                    articleList.add(Article(title, author, time, classify,url))
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }

//        articleList.add(Article("How to learn Android","JH","2021-6-1","start"))
//        articleList.add(Article("middle","JH","2021-6-1","start"))
//        articleList.add(Article("hard ","JH","2021-6-1","start"))
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment ProjectFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance() =
            HomePageFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

}