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
import com.example.wanandroid.adapter.HomeArticleAdapter
import com.example.wanandroid.adapter.QAAdapter
import com.example.wanandroid.databinding.FragmentQuestionAndAnswerBinding
import com.example.wanandroid.entity.Article
import okhttp3.OkHttpClient
import okhttp3.Request
import org.json.JSONArray
import org.json.JSONObject
import kotlin.concurrent.thread

// TODO: Rename parameter arguments, choose names that match
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [QuestionAndAnswerFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class QuestionAndAnswerFragment : Fragment() {

    private var QAList =ArrayList<Article>()
    private lateinit var binding:FragmentQuestionAndAnswerBinding

    init {

    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.fragment_question_and_answer, container, false)
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment QuestionAndAnswerFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            QuestionAndAnswerFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        init()
        //分割线
//        QARecyclerView.addItemDecoration(DividerItemDecoration(activity, DividerItemDecoration.VERTICAL))
//
//        Thread.sleep(1000)
//        //TODO: 处理加载数据的问题
//        val layoutManager = LinearLayoutManager(activity)
//        QARecyclerView.layoutManager = layoutManager
//        val adapter = QAAdapter(QAList)
//        QARecyclerView.adapter = adapter
    }


    private fun init(){
        val url :String="https://wanandroid.com/wenda/list/1/json"
        thread {
            val client= OkHttpClient()
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
                    val classify=jsonObject.getString("superChapterName")
                    val link=jsonObject.getString("link")
                    val description= jsonObject.getString("desc")

                    QAList.add(Article(title, author, time, classify,link,description))
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }


}