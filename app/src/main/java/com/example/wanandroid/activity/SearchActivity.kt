package com.example.wanandroid.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.WindowManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.wanandroid.R
import com.example.wanandroid.activity.baseactivity.BaseActivity
import com.example.wanandroid.adapter.SearchAdapter
import com.example.wanandroid.databinding.ActivitySearchBinding
import com.example.wanandroid.viewmodel.SearchViewModel


class SearchActivity : BaseActivity() {
    private lateinit var binding: ActivitySearchBinding
    private lateinit var viewModel: SearchViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySearchBinding.inflate(layoutInflater)
        viewModel = getViewModel(SearchViewModel::class.java)
        viewModel.articleList.observe(this,{
            binding.loadingPanel.visibility = View.GONE
            viewModel.list.addAll(it.data.datas)
        })
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        val view = binding.root
        binding.recyclerView.adapter = SearchAdapter(viewModel)
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.back.setOnClickListener {
            finish()
        }
        binding.editText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                viewModel.list.clear()

            }

            override fun afterTextChanged(p0: Editable?) {
                binding.loadingPanel.visibility = View.VISIBLE
                search(1, p0.toString())
            }

        })
        setContentView(view)
    }

    private fun search(page: Int, keyword: String) = viewModel.search(page, keyword)

}