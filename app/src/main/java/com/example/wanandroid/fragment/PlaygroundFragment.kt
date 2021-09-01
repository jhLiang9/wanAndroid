package com.example.wanandroid.fragment

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.example.wanandroid.R
import com.example.wanandroid.databinding.FragmentPlaygroundBinding

class PlaygroundFragment : Fragment() {

    companion object {
        fun newInstance() = PlaygroundFragment()
    }

    private lateinit var viewModel: PlaygroundViewModel
    private lateinit var binding: FragmentPlaygroundBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_playground, container, false)
        viewModel = ViewModelProvider(this).get(PlaygroundViewModel::class.java)
        return binding.root
    }


}