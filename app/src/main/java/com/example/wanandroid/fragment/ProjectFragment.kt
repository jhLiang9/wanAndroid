package com.example.wanandroid.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.wanandroid.R
import com.example.wanandroid.adapter.ProjectContentAdapter
import com.example.wanandroid.adapter.ProjectNavAdapter
import com.example.wanandroid.databinding.FragmentProjectBinding
import com.example.wanandroid.viewmodel.ProjectViewModel


class ProjectFragment : Fragment() {
    private val viewModel: ProjectViewModel by viewModels()
    private lateinit var binding: FragmentProjectBinding

    companion object {
        fun newInstance() = ProjectFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_project, container, false)
        initProjectNavigation()
        //初始化导航内容
        binding.recyclerViewLeft.adapter = ProjectNavAdapter(viewModel)
        binding.recyclerViewRight.adapter = ProjectContentAdapter(viewModel.rightList)
        viewModel.navList.observe(viewLifecycleOwner) {
            viewModel.leftList.addAll(it.data)
            binding.recyclerViewLeft.adapter?.notifyDataSetChanged()
        }

        viewModel.cid.observe(viewLifecycleOwner) {
            viewModel.getProjectContent(it, 1)
            binding.loadingPanel.visibility = View.VISIBLE
        }

        viewModel.projectList.observe(viewLifecycleOwner) {
            //TODO 根据是否刷新判断清除
            viewModel.rightList.clear()
            binding.loadingPanel.visibility = View.GONE
            viewModel.rightList.addAll(it.data.datas)
            binding.recyclerViewRight.adapter?.notifyDataSetChanged()
        }

        return binding.root
    }

    private fun initProjectNavigation() {
        viewModel.initProjectOverview()
    }

    private fun initProjectContent() {
        viewModel.getProjectContent(294, 1)
    }

}