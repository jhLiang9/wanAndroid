package com.example.wanandroid.fragment

import MyCollectionAdapter
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.wanandroid.R
import com.example.wanandroid.databinding.FragmentMyCollectionBinding
import com.example.wanandroid.fragment.basefragment.BaseFragment
import com.example.wanandroid.viewmodel.UserViewModel


class MyCollectionFragment :BaseFragment() {
    private lateinit var viewModel :UserViewModel
    private lateinit var binding: FragmentMyCollectionBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewModel = getViewModel(UserViewModel::class.java)
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_my_collection,container,false)
        binding.recyclerView.adapter = MyCollectionAdapter(viewModel)
        binding.recyclerView.layoutManager =LinearLayoutManager(context)
        InitFirstPageCollection()

        viewModel.collection.observe(viewLifecycleOwner,{
            viewModel.collectionList.addAll(it.data.datas)
            binding.recyclerView.adapter?.notifyDataSetChanged()
        })

        return binding.root
    }
    companion object {
        @JvmStatic
        fun newInstance()= MyCollectionFragment()
    }


    fun InitFirstPageCollection() = viewModel.getCollection(0)


}