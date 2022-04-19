package com.example.wanandroid.fragment

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewConfiguration
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.wanandroid.R
import com.example.wanandroid.activity.SearchActivity
import com.example.wanandroid.adapter.HomeArticleAdapter
import com.example.wanandroid.database.dao.ArticleDatabaseDao
import com.example.wanandroid.databinding.FragmentHomePageBinding
import com.example.wanandroid.entity.Article
import com.example.wanandroid.event.refresh.HomepageGoUpEvent
import com.example.wanandroid.fragment.basefragment.BaseFragment
import com.example.wanandroid.utils.EventBusUtil
import com.example.wanandroid.viewmodel.HomePageViewModel
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import kotlin.math.abs


class HomePageFragment : BaseFragment() {
    private val viewModel by lazy { getViewModel(HomePageViewModel::class.java) }
    private lateinit var binding: FragmentHomePageBinding
    private lateinit var database: ArticleDatabaseDao
    private val adapter by lazy { HomeArticleAdapter<Article>(viewModel) }

    companion object {
        fun newInstance() = HomePageFragment()
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
        binding.searchView.setOnClickListener {
            val intent = Intent(context, SearchActivity::class.java)
            startActivity(intent)
        }
        binding.searchBar.setOnClickListener {
            val intent = Intent(context, SearchActivity::class.java)
            startActivity(intent)
        }
//        database =
//            ArticleDatabase.getInstance(WanAndroidApplication.context).articleDatabaseDao

        var scrollDownDistance = 0
        var scrollUpDistance = 0
        binding.ArticleRecyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                if (newState == RecyclerView.SCROLL_STATE_DRAGGING) {
                    var offset: Int = recyclerView.computeVerticalScrollOffset()
                }

                //自动滚动开始
//                public static final int SCROLL_STATE_SETTLING = 2;
                super.onScrollStateChanged(recyclerView, newState)
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    var offset: Int = recyclerView.computeVerticalScrollOffset()

                    if (offset == 0) {
//                        binding.searchBar.setSelected(true);
                    }
                }

            }

            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                //向下滚动
                var temp = ViewConfiguration.get(context).scaledTouchSlop
                val listHeight = recyclerView.height
                if (dy > 10) {
                    if (abs(dy) > temp) {
                        scrollDownDistance = 0
                    }
                    //向下滚动
                    if (binding.searchBar.visibility == View.GONE) {
                        return
                    }
                    scrollUpDistance += dy
                    if (scrollUpDistance > listHeight / 2) {
                        hideSearch()
                    }
                } else if (dy < -10) {
                    if (abs(dy) > temp) {
                        scrollUpDistance = 0
                    }
                    if (binding.searchBar.visibility == View.VISIBLE) {
                        return
                    }
                    //向上滚动
                    scrollDownDistance += dy
                    showSearch()
                }
            }
        })

        initFirstPage()
        initView()
        initData()
        return binding.root
    }


    /**
     * 点击下方导航栏回到顶部
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onEvent(event: HomepageGoUpEvent) {
        binding.ArticleRecyclerView.scrollY = 0
        binding.ArticleRecyclerView.smoothScrollToPosition(0)
    }

    private fun initView() {
        binding.ArticleRecyclerView.layoutManager = LinearLayoutManager(activity)
        binding.refreshLayout.setOnRefreshListener {
            binding.refreshLayout.isRefreshing = false
            viewModel.refresh()
        }

    }

    /**
     * 刷新，重新加载加载数据
     */
    private fun initData() {

        binding.ArticleRecyclerView.adapter = adapter
        viewModel.articleList.observe(viewLifecycleOwner) {
            it?.let {
                adapter.appendData(it.datas)
                viewModel.pageCount = it.pageCount
            }
            binding.loadingPanel.visibility = View.GONE
        }
    }


    /**
     * 加载首页数据
     */
    private fun initFirstPage() {
        viewModel.getFirstPage()
    }

    private fun showSearch() {
        with(binding.searchBar) {
            visibility = View.VISIBLE
            translationY = -binding.searchBar.height.toFloat()
            alpha = 0f
            scaleY = 0f
            animate()
                .alpha(1f)
                .translationY(0f)
                .scaleY(1.0f)
                .setListener(null)
                .start()
        }
    }

    private fun hideSearch() {
        with(binding.searchBar) {
            alpha = 1f
            scaleX = 1f
            scaleY = 1f
            animate()
                .alpha(0f)
                .translationY(-binding.searchBar.height.toFloat())
                .scaleY(0f)
                .setDuration(100L)
                .setListener(object : AnimatorListenerAdapter() {
                    override fun onAnimationEnd(animation: Animator?) {
                        super.onAnimationEnd(animation)
                        binding.searchBar.visibility = View.GONE
                    }
                })
                .start()
        }

        binding.toolbar.animate()
            .translationY(0f)
            .setDuration(200L)
            .start()
    }


    override fun onDestroy() {
        super.onDestroy()
        EventBusUtil.unregister(this)
    }


}