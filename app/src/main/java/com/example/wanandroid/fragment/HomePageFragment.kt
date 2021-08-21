package com.example.wanandroid.fragment

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.wanandroid.R
import com.example.wanandroid.adapter.HomeArticleAdapter
import com.example.wanandroid.databinding.FragmentHomePageBinding
import com.example.wanandroid.entity.Article
import com.example.wanandroid.event.HomePageDataReadyEvent
import com.example.wanandroid.event.refresh.HomepageGoUpEvent
import com.example.wanandroid.fragment.vm.HomePageFragmentVM
import com.example.wanandroid.utils.EventBusUtil

import com.example.wanandroid.utils.HtmlElementUtil
import com.example.wanandroid.viewmodel.HomePageViewModel
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode


class HomePageFragment : HomePageFragmentVM() {

    private lateinit var binding: FragmentHomePageBinding
    companion object{
        fun newInstance(bundle:Bundle):HomePageFragment{
            val fragment = HomePageFragment()
            fragment.arguments = bundle
            return fragment
        }

    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        EventBusUtil.register(this)
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_home_page, container, false)
        initFirstPage()
        initView()
        initData()
        return binding.root
    }

    override fun onDestroy() {
        super.onDestroy()
        EventBusUtil.unregister(this)
    }

    /**
     * 点击下方导航栏回到顶部
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onEvent(event: HomepageGoUpEvent){
        binding.ArticleRecyclerView.scrollY = 0
        binding.ArticleRecyclerView.smoothScrollToPosition(0)
        //TODO 在首页时，进行刷新
    }

    private fun initView(){
        binding.ArticleRecyclerView.addItemDecoration(DividerItemDecoration(activity, DividerItemDecoration.VERTICAL))
        binding.ArticleRecyclerView.layoutManager = LinearLayoutManager(activity)
        binding.refreshLayout.setOnRefreshListener {
            refresh()
        }

    }

    private fun initData(){
        binding.ArticleRecyclerView.adapter = HomeArticleAdapter(viewModel.presentList,viewModel)

        viewModel.articleList.observe(viewLifecycleOwner, Observer {
            if(it.errorCode==0){
                viewModel.presentList.addAll(it.data.datas)
                binding.ArticleRecyclerView.adapter?.notifyDataSetChanged()
                binding.loadingPanel.visibility=View.GONE
            }
            else{
                Toast.makeText(context,it.errorMessage,Toast.LENGTH_SHORT).show()
            }
        })

    }



    private fun refresh(){
        //清除数据集，重新加载
        viewModel.presentList.clear()
        viewModel.refresh()
        binding.refreshLayout.isRefreshing=false
    }

    /**
     * 加载首页数据
     */
    private fun initFirstPage() = viewModel.getArticlesByPage(0)

    private fun getArticlesByPage(page: Int) = viewModel.getArticlesByPage(page)

}