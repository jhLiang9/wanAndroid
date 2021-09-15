package com.example.wanandroid.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.wanandroid.R
import com.example.wanandroid.adapter.SystemAdapter
import com.example.wanandroid.database.SystemDatabase
import com.example.wanandroid.database.dao.SystemDatabaseDao
import com.example.wanandroid.databinding.FragmentSystemBinding
import com.example.wanandroid.entity.Tree
import com.example.wanandroid.entity.TreeList
import com.example.wanandroid.fragment.basefragment.BaseFragment
import com.example.wanandroid.service.AppService
import com.example.wanandroid.service.ServiceCreator
import com.example.wanandroid.viewmodel.SystemViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class SystemFragment : BaseFragment() {

    private lateinit var binding: FragmentSystemBinding
    private lateinit var viewModel: SystemViewModel
    private lateinit var database: SystemDatabaseDao

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewModel = ViewModelProvider(this).get(SystemViewModel::class.java)
        database = SystemDatabase.getInstance(requireContext()).systemDatabaseDao
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
                    binding.systemModule.adapter = SystemAdapter( viewModel)
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