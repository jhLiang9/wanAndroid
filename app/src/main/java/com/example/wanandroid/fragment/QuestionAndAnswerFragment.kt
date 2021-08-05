package com.example.wanandroid.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.wanandroid.R
import com.example.wanandroid.adapter.QAAdapter
import com.example.wanandroid.databinding.FragmentQuestionAndAnswerBinding
import com.example.wanandroid.entity.Article
import okhttp3.OkHttpClient
import okhttp3.Request
import org.json.JSONArray
import org.json.JSONObject
import kotlin.concurrent.thread



class QuestionAndAnswerFragment : Fragment() {

    private var QAList =ArrayList<Article>()
    private lateinit var binding:FragmentQuestionAndAnswerBinding
    private val client= OkHttpClient()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_question_and_answer,container,false)
        binding.QARecyclerView.addItemDecoration(DividerItemDecoration(activity, DividerItemDecoration.VERTICAL))
        return binding.root
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        init()
        //分割线



        val layoutManager = LinearLayoutManager(activity)
        binding.QARecyclerView.layoutManager = layoutManager
        val adapter = QAAdapter(QAList)
        binding.QARecyclerView.adapter = adapter
    }


    private fun init(){
        val url :String="https://wanandroid.com/wenda/list/1/json"
        thread {

            val request = Request.Builder()
                .url(url)
                .build()
            val response = client.newCall(request).execute()
            response.let {

            }
            var responseData = response.body?.string()

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
                    val superChapterName=jsonObject.getString("superChapterName")
                    val link=jsonObject.getString("link")
                    val description= jsonObject.getString("desc")
                    val id = jsonObject.getInt("id")
                    QAList.add(Article(id,title, author, time, superChapterName,link,description))
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }.join()
    }


}