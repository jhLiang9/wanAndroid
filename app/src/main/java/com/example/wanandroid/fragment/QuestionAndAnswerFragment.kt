package com.example.wanandroid.fragment

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.wanandroid.R
import com.example.wanandroid.adapter.QAAdapter
import com.example.wanandroid.databinding.FragmentQuestionAndAnswerBinding
import com.example.wanandroid.event.refresh.QARefreshEvent
import com.example.wanandroid.fragment.basefragment.BaseFragment
import com.example.wanandroid.utils.EventBusUtil
import com.example.wanandroid.viewmodel.QAViewModel
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

class QuestionAndAnswerFragment : BaseFragment() {
    private lateinit var binding: FragmentQuestionAndAnswerBinding
    private val viewModel by lazy { getViewModel(QAViewModel::class.java) }
    private val adapter by lazy { QAAdapter() }

    companion object {
        @JvmStatic
        fun newInstance() = QuestionAndAnswerFragment()
    }


    @SuppressLint("NotifyDataSetChanged")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        EventBusUtil.register(this)

        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_question_and_answer,
            container,
            false
        )
        initData()
        initView()
        initViewModel()
        return binding.root
    }

    private fun initView() {
        binding.recyclerView.layoutManager = LinearLayoutManager(activity)
        binding.recyclerView.adapter = adapter
        binding.recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                if (recyclerView.computeVerticalScrollExtent() + recyclerView.computeVerticalScrollOffset() >= recyclerView.computeVerticalScrollRange()) {
                    if (viewModel.hasNextPage()) {
                        viewModel.getNextPage()
                    }
                }
            }
        })
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun initViewModel() {
        viewModel.list.observe(viewLifecycleOwner) {
            adapter.dataList.addAll(it.data.datas)
            binding.recyclerView.adapter?.notifyDataSetChanged()
            binding.loadingPanel.visibility = View.GONE
        }
    }

    private fun initData() {
        viewModel.init()
    }


    override fun onDestroy() {
        super.onDestroy()
        EventBusUtil.unregister(this)
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onEvent(event: QARefreshEvent) {
        binding.loadingPanel.visibility = View.VISIBLE
        initData()
    }


}