package com.example.wanandroid.fragment

import android.annotation.SuppressLint
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
import com.example.wanandroid.adapter.QAAdapter
import com.example.wanandroid.databinding.FragmentQuestionAndAnswerBinding
import com.example.wanandroid.entity.Article
import com.example.wanandroid.event.QAEvent
import com.example.wanandroid.event.refresh.QARefreshEvent
import kotlinx.coroutines.runBlocking
import okhttp3.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import org.json.JSONArray
import org.json.JSONObject
import java.io.IOException

class QuestionAndAnswerFragment : Fragment() {

    private var qaList = ArrayList<Article>()
    private lateinit var binding: FragmentQuestionAndAnswerBinding
    private val client = OkHttpClient()

    companion object {
        private var instance: QuestionAndAnswerFragment? = null
        fun getInstance(): QuestionAndAnswerFragment {
            if (instance == null) {
                synchronized(this) {
                    instance = QuestionAndAnswerFragment()
                }
            }
            return instance as QuestionAndAnswerFragment
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        EventBus.getDefault().register(this)
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_question_and_answer, container, false)
        Log.d("binder",binding.QARecyclerView.toString())
        binding.QARecyclerView.addItemDecoration(
            DividerItemDecoration(
                activity,
                DividerItemDecoration.VERTICAL
            )
        )

        init()
        //分割线

        val layoutManager = LinearLayoutManager(activity)
        binding.QARecyclerView.layoutManager = layoutManager

        binding.QARecyclerView.adapter = QAAdapter(qaList)

        return binding.root
    }

    override fun onDestroy() {
        super.onDestroy()
        EventBus.getDefault().unregister(this)
    }

    
    @SuppressLint("NotifyDataSetChanged")
    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onEvent(event: QAEvent){
        binding.QARecyclerView.adapter?.notifyDataSetChanged()
        binding.loadingPanel.visibility=View.GONE
    }


    @SuppressLint("NotifyDataSetChanged")
    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onEvent(event: QARefreshEvent){
        binding.loadingPanel.visibility=View.VISIBLE
        init()
    }


    private fun init() {
        val url = "https://wanandroid.com/wenda/list/1/json"
        val request = Request.Builder()
            .url(url)
            .build()
        val call: Call = client.newCall(request)

        call.enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                e.printStackTrace()
            }

            override fun onResponse(call: Call, response: Response) {
                val jsonData = JSONObject(response.body?.string()).getString("data")
                val datas = JSONObject(jsonData).getString("datas")

                val jsonArray = JSONArray(datas)
                for (i in 0 until jsonArray.length()) {
                    val jsonObject = jsonArray.getJSONObject(i)
                    val title = jsonObject.getString("title")
                    val time = jsonObject.getString("niceDate")
                    var author = jsonObject.getString("author")
                    if (author == "") {
                        author = jsonObject.getString("shareUser")
                    }
                    val superChapterName = jsonObject.getString("superChapterName")
                    val link = jsonObject.getString("link")
                    val description = jsonObject.getString("desc")
                    val id = jsonObject.getInt("id")

                    qaList.add(Article(id,title, author,time, superChapterName,link,description))

                }
                EventBus.getDefault().post(QAEvent())
            }

        })

    }


}