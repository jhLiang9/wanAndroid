package com.example.wanandroid.activity

import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import androidx.annotation.RequiresApi
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.wanandroid.R
import com.example.wanandroid.activity.baseactivity.BaseActivity
import com.example.wanandroid.adapter.SearchAdapter
import com.example.wanandroid.databinding.ActivitySearchBinding
import com.example.wanandroid.viewmodel.SearchViewModel


class SearchActivity : BaseActivity() {
    private lateinit var binding: ActivitySearchBinding
    private lateinit var viewModel: SearchViewModel
    private val adapter by lazy { SearchAdapter() }

    companion object {
        @JvmStatic
        fun start(context: Context?) {
            val intent = Intent(context, SearchActivity::class.java)
            context?.startActivity(intent)
        }
    }

    @RequiresApi(Build.VERSION_CODES.R)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_search)

        viewModel = getViewModel(SearchViewModel::class.java)
        viewModel.articleList.observe(this) {
            binding.loadingPanel.visibility = View.GONE
            if (it == null || it.data.datas.isEmpty()) {
                adapter.clearData()
            } else {
                adapter.dataList.addAll(it.data.datas)
            }
            adapter.notifyDataSetChanged()
        }

        initView()
    }

    private fun initView() {
        binding.recyclerView.adapter = adapter
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.back.setOnClickListener {
            finish()
        }

        binding.editText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                adapter.clearData()
                adapter.notifyDataSetChanged()
            }

            override fun afterTextChanged(keyword: Editable?) {
                binding.loadingPanel.visibility = View.VISIBLE
                search(1, keyword.toString())
            }
        })
    }

    private fun search(page: Int, keyword: String) = viewModel.search(page, keyword)

}