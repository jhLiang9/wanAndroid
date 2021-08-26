package com.example.wanandroid.fragment

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.example.wanandroid.R
import com.example.wanandroid.databinding.FragmentNaviBinding
import com.example.wanandroid.viewmodel.NaviViewModel

class NaviFragment : Fragment() {

    companion object {
        fun newInstance() = NaviFragment()
    }

    private lateinit var viewModel: NaviViewModel
    private lateinit var binding: FragmentNaviBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_navi, container, false)
        viewModel = ViewModelProvider(this).get(NaviViewModel::class.java)

        return binding.root
    }

    private fun init() {
        initData()
    }

    private fun initData() {
        TODO("Not yet implemented")
    }

}