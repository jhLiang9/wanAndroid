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
import com.example.wanandroid.database.ArticleDatabase
import com.example.wanandroid.database.dao.ArticleDatabaseDao
import com.example.wanandroid.databinding.FragmentHomePageBinding
import com.example.wanandroid.entity.Article
import com.example.wanandroid.entity.data.ArticleData
import com.example.wanandroid.event.HomePageDataReadyEvent
import com.example.wanandroid.event.refresh.HomepageGoUpEvent
import com.example.wanandroid.fragment.vm.HomePageFragmentVM
import com.example.wanandroid.utils.EventBusUtil

import com.example.wanandroid.utils.HtmlElementUtil
import com.example.wanandroid.viewmodel.HomePageViewModel
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.*
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.functions.Consumer
import io.reactivex.rxjava3.schedulers.Schedulers
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import org.reactivestreams.Subscriber
import org.reactivestreams.Subscription
import kotlin.concurrent.thread


class HomePageFragment : HomePageFragmentVM() {

    private lateinit var binding: FragmentHomePageBinding
    private lateinit var database: ArticleDatabaseDao

    companion object {
        fun newInstance(bundle: Bundle): HomePageFragment {
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
        database =
            ArticleDatabase.getInstance(requireContext().applicationContext).articleDatabaseDao
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
    fun onEvent(event: HomepageGoUpEvent) {
        binding.ArticleRecyclerView.scrollY = 0
        binding.ArticleRecyclerView.smoothScrollToPosition(0)
        //TODO 在首页时，进行刷新
    }

    private fun initView() {
        binding.ArticleRecyclerView.layoutManager = LinearLayoutManager(activity)
        binding.refreshLayout.setOnRefreshListener {
            refresh()
        }

    }

    private fun initData() {
        binding.ArticleRecyclerView.adapter = HomeArticleAdapter(viewModel.presentList, viewModel)
        viewModel.articleList.observe(viewLifecycleOwner, Observer {
            //TODO observe 发生变化时 只需要notify就行
            viewModel.presentList.addAll(it.data.datas)
            viewModel.pageCount = it.data.pageCount

            Observable.create(ObservableOnSubscribe<Article> { emitter ->
                for (item in it.data.datas) {
                    emitter?.onNext(item)
                }
                emitter?.onComplete()
            })
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .subscribe { t ->
                    if (t != null && database.get(t.id) != null) {
                        database.insert(t)
                    }
                }
            binding.ArticleRecyclerView.adapter?.notifyDataSetChanged()

            binding.loadingPanel.visibility = View.GONE
        })

    }

    /**
     * 刷新，重新加载加载数据
     */
    private fun refresh() {
        viewModel.refresh()
        binding.refreshLayout.isRefreshing = false
    }

    /**
     * 加载首页数据
     */
    private fun initFirstPage() {
        viewModel.getArticlesByPage(0)
    }


}