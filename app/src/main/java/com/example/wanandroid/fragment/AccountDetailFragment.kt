package com.example.wanandroid.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.wanandroid.R
import com.example.wanandroid.WanAndroidApplication

class AccountDetailFragment : Fragment() {
    val application = WanAndroidApplication

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_account_detail, container, false)
    }


    companion object {
        @JvmStatic
        fun newInstance() = AccountDetailFragment()
    }
}