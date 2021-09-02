package com.example.wanandroid.fragment


import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.wanandroid.R
import com.example.wanandroid.adapter.ProjectNavAdapter
import com.example.wanandroid.databinding.FragmentProjectListBinding
import com.example.wanandroid.entity.Tree
import com.example.wanandroid.viewmodel.ProjectViewModel



class ProjectListFragment : Fragment() {
    private val navList = ArrayList<Tree>()
    //委托
    private val viewModel: ProjectViewModel by viewModels()
    private lateinit var binding: FragmentProjectListBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_project_list, container, false)

        initProjectNavigation()
        //初始化导航内容
        binding.nav.layoutManager = LinearLayoutManager(activity)
        binding.nav.adapter = ProjectNavAdapter(viewModel)
        viewModel.navList.observe(viewLifecycleOwner,{
            navList.addAll(it.data)
            binding.nav.adapter?.notifyDataSetChanged()
        })
        return binding.root
    }



    private fun initProjectNavigation() = viewModel.initProjectOverview()

}