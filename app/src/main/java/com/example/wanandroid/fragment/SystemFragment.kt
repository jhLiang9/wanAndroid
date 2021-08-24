package com.example.wanandroid.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.wanandroid.R
import com.example.wanandroid.adapter.SystemAdapter
import com.example.wanandroid.database.SystemDatabase
import com.example.wanandroid.database.dao.SystemDatabaseDao
import com.example.wanandroid.databinding.FragmentSystemBinding
import com.example.wanandroid.entity.Tree
import com.example.wanandroid.entity.list.TreeList
import com.example.wanandroid.viewmodel.SystemViewModel
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Flowable
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.schedulers.Schedulers
import java.util.concurrent.Executors
import kotlin.concurrent.thread


class SystemFragment : Fragment() {

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
        binding.systemModule.layoutManager = LinearLayoutManager(context)
        return binding.root
    }

    private fun initData() {
        var list = ArrayList<Tree>()
        thread {
            list = ArrayList(database.getAllSystemTree())
        }.join()
        if (list.isEmpty()) {
            Log.i("system", "not cache")
            viewModel.getData()
        }

        binding.systemModule.adapter = SystemAdapter(viewModel.overview.value!!.data, viewModel)
        viewModel.overview.observe(viewLifecycleOwner, {
            list = viewModel.overview.value!!.data
            thread {
                for (i in 0..list.size - 1) {
                    database.insert(list[i])
                    Log.i("system", list[i].toString())
                }
            }
            binding.systemModule.adapter?.notifyDataSetChanged()
        })
    }

    private fun initView() {

    }

}