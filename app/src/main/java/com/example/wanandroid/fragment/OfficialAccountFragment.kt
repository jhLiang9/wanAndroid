package com.example.wanandroid.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.wanandroid.R
import com.example.wanandroid.databinding.FragmentOfficialAccountBinding
import com.example.wanandroid.entity.WXAccount
import com.example.wanandroid.fragment.basefragment.BaseFragment
import com.example.wanandroid.viewmodel.OfficialAccountViewModel
import com.google.android.material.tabs.TabLayoutMediator

class OfficialAccountFragment : BaseFragment() {

    companion object {
        @JvmStatic
        fun newInstance() = OfficialAccountFragment()
    }

    private lateinit var viewModel: OfficialAccountViewModel
    private lateinit var binding: FragmentOfficialAccountBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val tabs = ArrayList<WXAccount>()
        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_official_account, container, false)
        viewModel = getViewModel(OfficialAccountViewModel::class.java)

        viewModel.wxAccountList.observe(viewLifecycleOwner) {
            if (it.data != null) {
                tabs.addAll(it.data)
                binding.wxViewPager.adapter = object : FragmentStateAdapter(this) {
                    override fun getItemCount(): Int = tabs.size

                    override fun createFragment(position: Int): Fragment {
                        return WxArticleFragment.newInstance(tabs[position].id, 1)
                    }

                }
                TabLayoutMediator(binding.wxTabs, binding.wxViewPager) { tab, position ->
                    tab.text = tabs[position].name
                    tab.view.setOnClickListener {
                        binding.wxViewPager.currentItem = position
                    }
                }.attach()

            }
        }

        initTabs()
        return binding.root
    }

    private fun initTabs() = viewModel.getAccounts()


}
