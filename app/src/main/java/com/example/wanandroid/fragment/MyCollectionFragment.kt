package com.example.wanandroid.fragment

import MyCollectionAdapter
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.wanandroid.R
import com.example.wanandroid.databinding.FragmentMyCollectionBinding
import com.example.wanandroid.fragment.basefragment.BaseFragment
import com.example.wanandroid.viewmodel.UserViewModel


class MyCollectionFragment : BaseFragment() {
    private lateinit var viewModel: UserViewModel
    private lateinit var binding: FragmentMyCollectionBinding

    companion object {
        @JvmStatic
        fun newInstance() = MyCollectionFragment()
    }



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewModel = getViewModel(UserViewModel::class.java)
        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_my_collection, container, false)
        val adapter = MyCollectionAdapter(viewModel)
        binding.recyclerView.layoutManager = LinearLayoutManager(context)
        binding.recyclerView.adapter = adapter
        InitFirstPageCollection()

        viewModel.collection.observe(viewLifecycleOwner) {
            viewModel.collectionList.addAll(it.data.datas)
            adapter.notifyDataSetChanged()
        }

        return binding.root
    }

    private fun InitFirstPageCollection() = viewModel.getCollection(0)


}