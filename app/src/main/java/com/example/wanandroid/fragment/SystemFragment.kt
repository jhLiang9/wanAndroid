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
import com.example.wanandroid.databinding.FragmentSystemBinding
import com.example.wanandroid.entity.Tree

import com.example.wanandroid.viewmodel.SystemViewModel


class SystemFragment : Fragment() {

    private lateinit var binding:FragmentSystemBinding
    private lateinit var viewModel :SystemViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewModel = ViewModelProvider(this).get(SystemViewModel::class.java)
        binding =DataBindingUtil.inflate(inflater,R.layout.fragment_system,container,false)
        val t1=Tree(ArrayList(),1,2,"Kotlin",1,2,false,4)
        val al =ArrayList<Tree>()
        al.add(t1)
        val t2=Tree(al,1,2,"Androdi",1,2,false,4)
        val test= ArrayList<Tree>()
        test.add(t2)

        initLayoutData()
        Thread.sleep(1000L)
        //TODO 每次加载时间太长了 使用cache
        binding.systemModule.layoutManager = LinearLayoutManager(context)
        binding.systemModule.adapter = SystemAdapter(viewModel.presentList,viewModel)//viewModel.presentList
        viewModel.overview.observe(viewLifecycleOwner,{
            viewModel.presentList.addAll(it.data)
            binding.systemModule.adapter?.notifyDataSetChanged()
        })
        return binding.root
    }

    private fun initLayoutData() = viewModel.getData()

}