package com.example.wanandroid

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.WindowManager
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_main.*
import okhttp3.OkHttpClient
import okhttp3.Request
import org.json.JSONArray
import org.json.JSONObject
import kotlin.concurrent.thread

class MainActivity : AppCompatActivity() {
    private  val articleList=ArrayList<Article>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);//
        setContentView(R.layout.activity_main)
        test.setOnClickListener {
            intent = Intent(this,TestActivity::class.java)
            startActivity(intent)
        }
        initArticles() // 初始化文章数据
        val layoutManager = LinearLayoutManager(this)
        recyclerView.layoutManager = layoutManager
        val adapter = HomeArticleAdapter(articleList)
        recyclerView.adapter = adapter

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
            val datas=JSONObject(jsondata).getString("datas")


            try {
                val jsonArray = JSONArray(datas)
                for (i in 0 until jsonArray.length()) {
                    val jsonObject = jsonArray.getJSONObject(i)

                    val title = jsonObject.getString("title")
                    val time = jsonObject.getString("niceDate")
                    val author = jsonObject.getString("author")
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
}