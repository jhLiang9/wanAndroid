package com.example.wanandroid.fragment

import android.os.Bundle
import android.util.SparseArray
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.wanandroid.R
import com.example.wanandroid.databinding.FragmentStudyBinding
import com.google.android.material.tabs.TabLayoutMediator


class StudyFragment : Fragment() {

    companion object {
        fun newInstance() = StudyFragment()
    }

    private lateinit var binding: FragmentStudyBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_study, container, false)
        val tabs = listOf("项目", "公众号")
        val sparseArray = SparseArray<Fragment>(2)
        sparseArray.put(0, ProjectFragment.newInstance())
        sparseArray.put(1, OfficialAccountFragment.newInstance())
        binding.viewPager.adapter = object : FragmentStateAdapter(this) {
            override fun getItemCount(): Int = 2

            override fun createFragment(position: Int): Fragment = sparseArray.get(position)

        }
        TabLayoutMediator(binding.tabs, binding.viewPager) { tab, position ->
            tab.text = tabs[position]
            tab.view.setOnClickListener {
                binding.viewPager.currentItem = position
            }

        }.attach()
        return binding.root
    }

}