package com.example.wanandroid.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.wanandroid.R
import com.example.wanandroid.adapter.HomeArticleAdapter
import com.example.wanandroid.entity.Article
import kotlinx.android.synthetic.main.fragment_home_page.*
import okhttp3.OkHttpClient
import okhttp3.Request
import org.json.JSONArray
import org.json.JSONObject
import kotlin.concurrent.thread


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [HomePageFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class HomePageFragment : Fragment() {


    private  val articleList=ArrayList<Article>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        initRecyclerView()
        initArticles() // 初始化文章数据

        return inflater.inflate(R.layout.fragment_home_page, container, false)
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment HomePageFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            HomePageFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
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
                    articleList.add(Article(title, author, time, classify))
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }

//        articleList.add(Article("How to learn Android","JH","2021-6-1","start"))
//        articleList.add(Article("middle","JH","2021-6-1","start"))
//        articleList.add(Article("hard ","JH","2021-6-1","start"))
    }

    private fun initRecyclerView(){
            //https://blog.csdn.net/zhuchenglin830/article/details/82286109  Fragment中RecyclerView的使用解析
        val adapter = HomeArticleAdapter(articleList)
        ArticleRecyclerView.adapter=adapter
        val layoutManager = LinearLayoutManager(activity)
        ArticleRecyclerView.layoutManager = layoutManager


    }
}