package com.example.wanandroid.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.wanandroid.R
import com.example.wanandroid.adapter.SystemAdapter
import com.example.wanandroid.database.SystemDatabase
import com.example.wanandroid.database.dao.SystemDatabaseDao
import com.example.wanandroid.databinding.FragmentSystemBinding
import com.example.wanandroid.entity.TreeList
import com.example.wanandroid.fragment.basefragment.BaseFragment
import com.example.wanandroid.viewmodel.SystemViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class SystemFragment : BaseFragment() {

    private lateinit var binding: FragmentSystemBinding
    private lateinit var database: SystemDatabaseDao
    private val viewModel: SystemViewModel by lazy {
        getViewModel(SystemViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

//        database = SystemDatabase.getInstance(requireContext()).systemDatabaseDao
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_system, container, false)
        initData()
        return binding.root
    }

    private fun initData() {
        appService.getSystemTree().enqueue(object : Callback<TreeList> {
            override fun onResponse(call: Call<TreeList>, response: Response<TreeList>) {
                val body = response.body()
                if (body != null) {
                    viewModel.list.addAll(body.data)
                    binding.systemModule.adapter = SystemAdapter(viewModel)
                    binding.systemModule.layoutManager = LinearLayoutManager(context)
                }
                binding.loadingPanel.visibility = View.GONE
            }

            override fun onFailure(call: Call<TreeList>, t: Throwable) {
                t.printStackTrace()
            }

        })
    }


}