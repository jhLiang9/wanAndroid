package com.example.wanandroid.fragment

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.wanandroid.R
import com.example.wanandroid.adapter.QAAdapter
import com.example.wanandroid.databinding.FragmentQuestionAndAnswerBinding
import com.example.wanandroid.entity.Article
import com.example.wanandroid.event.QAEvent
import com.example.wanandroid.event.refresh.QARefreshEvent
import com.example.wanandroid.viewmodel.QAViewModel
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
    private lateinit var viewModel:QAViewModel

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


    @SuppressLint("NotifyDataSetChanged")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        EventBus.getDefault().register(this)
        viewModel = ViewModelProvider(this).get(QAViewModel::class.java)
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_question_and_answer, container, false)
//        binding.QARecyclerView.addItemDecoration(
//            DividerItemDecoration(
//                activity,
//                DividerItemDecoration.VERTICAL
//            )
//        )
        init()
        //分割线
        val layoutManager = LinearLayoutManager(activity)
        binding.QARecyclerView.layoutManager = layoutManager
        binding.QARecyclerView.adapter = QAAdapter(qaList)
        viewModel.list.observe(viewLifecycleOwner,{
            qaList.addAll(it.data.datas)
            binding.QARecyclerView.adapter?.notifyDataSetChanged()
            binding.loadingPanel.visibility=View.GONE
        })
        return binding.root
    }

    override fun onDestroy() {
        super.onDestroy()
        EventBus.getDefault().unregister(this)
    }



    @SuppressLint("NotifyDataSetChanged")
    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onEvent(event: QARefreshEvent){
        binding.loadingPanel.visibility=View.VISIBLE
        init()
    }


    private fun init() {
        viewModel.init()
    }


}