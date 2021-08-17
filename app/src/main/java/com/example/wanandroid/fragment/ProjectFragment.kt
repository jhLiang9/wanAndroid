package com.example.wanandroid.fragment

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels

import com.example.wanandroid.R
import com.example.wanandroid.adapter.HomeArticleAdapter
import com.example.wanandroid.databinding.FragmentProjectBinding
import com.example.wanandroid.event.ProjectContentEvent
import com.example.wanandroid.event.ProjectListEvent
import com.example.wanandroid.utils.EventBusUtil
import com.example.wanandroid.viewmodel.ProjectViewModel
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode


class ProjectFragment: Fragment() {

    private val viewModel : ProjectViewModel by viewModels()
    private lateinit var binding : FragmentProjectBinding


    companion object {
        @Volatile
        private var INSTANCE: ProjectFragment? = null
        fun getInstance(context: Context): ProjectFragment {
            synchronized(this) {
                var instance = INSTANCE
                if (instance == null) {
                    instance = ProjectFragment()
                    INSTANCE = instance
                }
                return instance
            }
        }
    }





    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_project,container,false)
        EventBusUtil.register(this)
        return binding.root
    }



    override fun onDestroy() {
        EventBusUtil.unregister(this)
        super.onDestroy()
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onEvent(event:ProjectContentEvent){
        binding.loadingPanel.visibility=View.GONE
        binding.divide.visibility=View.VISIBLE
    }
    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onEvent(event:ProjectListEvent){
        binding.loadingPanel.visibility=View.GONE
        binding.divide.visibility=View.VISIBLE
    }

}