package com.example.wanandroid.fragment

import android.os.Bundle
import android.util.SparseArray
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.wanandroid.R
import com.example.wanandroid.databinding.FragmentInteractionBinding
import com.google.android.material.tabs.TabLayoutMediator

class InteractionFragment : Fragment() {

    companion object {
        fun newInstance() = InteractionFragment()
    }

    private lateinit var binding: FragmentInteractionBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_interaction, container, false)
        val sparseArray = SparseArray<Fragment>(2)
        val tabs = listOf("问答", "广场")
        binding.viewPager.adapter = object : FragmentStateAdapter(this) {
            override fun getItemCount(): Int = 2

            //TODO 判断是否存活，存活无须多次创建
            override fun createFragment(position: Int): Fragment = sparseArray.get(position)
        }
        sparseArray.put(0, QuestionAndAnswerFragment.newInstance())
        sparseArray.put(1, PlaygroundFragment.newInstance())
        TabLayoutMediator(binding.tabs, binding.viewPager) { tab, position ->
            tab.text = tabs[position]
//            tab.icon = ResourcesCompat.getDrawable(resources,icons[position],null)
            tab.view.setOnClickListener {
                binding.viewPager.currentItem = position
                if (tab.isSelected) {
                    when (position) {
//                        0-> EventBusUtil.post(HomepageGoUpEvent)
//                        1->EventBusUtil.post(SystemRefreshEvent)
                    }
                }
            }
        }.attach()
        return binding.root
    }


}