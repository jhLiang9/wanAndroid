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
import com.example.wanandroid.utils.EventBusUtil

import com.example.wanandroid.utils.HtmlElementUtil
import com.example.wanandroid.viewmodel.HomePageViewModel
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode


class HomePageFragment : Fragment() {

    private lateinit var binding: FragmentHomePageBinding
    private lateinit var viewModel :HomePageViewModel

    companion object {
        private val articleList = ArrayList<Article>()
        private var instance: HomePageFragment? = null
        fun getInstance(): HomePageFragment {
            if (instance == null) {
                synchronized(this) {
                    instance = HomePageFragment()
                    return instance!!
                }
            }
            return instance!!
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        EventBusUtil.register(this)
        viewModel=  ViewModelProvider(this).get(HomePageViewModel::class.java)

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_home_page, container, false)

        binding.ArticleRecyclerView.addItemDecoration(DividerItemDecoration(activity, DividerItemDecoration.VERTICAL))
        //加载首页数据
        initFirstPage()

        binding.ArticleRecyclerView.layoutManager = LinearLayoutManager(activity)
        binding.ArticleRecyclerView.adapter = HomeArticleAdapter(articleList)
        binding.refreshLayout.setOnRefreshListener {
            refresh()
        }

        viewModel.articleList.observe(viewLifecycleOwner, Observer {
            if(it.errorCode==0){
                articleList.addAll(it.data.datas)
                binding.ArticleRecyclerView.adapter?.notifyDataSetChanged()
                binding.loadingPanel.visibility=View.GONE
            }
            else{
                Toast.makeText(context,it.errorMessage,Toast.LENGTH_SHORT).show()
            }
        })
        return binding.root
    }

    override fun onDestroy() {
        super.onDestroy()
        EventBusUtil.unregister(this)
    }

    //点击下方导航栏回到顶部
    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onEvent(event: HomepageGoUpEvent){
        binding.ArticleRecyclerView.scrollY = 0
        binding.ArticleRecyclerView.smoothScrollToPosition(0)
        //TODO 在首页时，进行刷新
    }

    private fun refresh(){
        //清除数据集，重新加载
        articleList.clear()
        viewModel.refresh()
        binding.refreshLayout.isRefreshing=false
    }


    private fun initFirstPage() = getArticlesByPage(0)

    private fun getArticlesByPage(page: Int) = viewModel.getArticlesByPage(page)

}