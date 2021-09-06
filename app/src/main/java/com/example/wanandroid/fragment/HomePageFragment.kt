package com.example.wanandroid.fragment

import android.animation.Animator
import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
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
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import android.animation.AnimatorListenerAdapter
import android.graphics.Color
import android.os.Build
import android.view.*
import androidx.core.widget.NestedScrollView
import com.google.android.material.appbar.AppBarLayout
import kotlin.math.abs


class HomePageFragment : HomePageFragmentVM() {
    var statusAlpha=0
    private lateinit var binding: FragmentHomePageBinding
    private lateinit var database: ArticleDatabaseDao

    companion object {
        fun newInstance(bundle: Bundle): HomePageFragment {
            val fragment = HomePageFragment()
            fragment.arguments = bundle
            return fragment
        }

    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)

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

//        binding.nsvLayout.setOnScrollChangeListener{ _: NestedScrollView?, _: Int, scrollY: Int, _: Int, _: Int ->
//            val headerHeight = binding.ArticleRecyclerView.height
//            val scrollDistance = Math.min(scrollY, headerHeight)
//            statusAlpha = (255F * scrollDistance / headerHeight).toInt()
//            setTopBackground()
//
//
//        }
//        (activity as AppCompatActivity?)!!.setSupportActionBar(binding.toolbar)

//        binding.appBar.addOnOffsetChangedListener(object: AppBarLayout.OnOffsetChangedListener {
//            var isShow :Boolean = false
//            var scrollRange = -1
//            override fun onOffsetChanged(appBarLayout: AppBarLayout?, verticalOffset: Int) {
//                if(scrollRange==-1){
//                    if (appBarLayout != null) {
//                        scrollRange = appBarLayout.totalScrollRange
//                    }
//                }
//                if(scrollRange+verticalOffset ==0){
//                    isShow = true
//                }else if(isShow){
//                    isShow=false
//                }
//            }
//
//        })
//        var scrollDownDistance =0
//        var scrollUpDistance =0
//        binding.ArticleRecyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
//            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
//                super.onScrollStateChanged(recyclerView, newState)
//            }
//
//            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
//                //向下滑动
//                val listHeight=recyclerView.height
//                if (dy > 0) {
//                    val offset = recyclerView.computeVerticalScrollOffset()
//                    if (offset > 10) {
//                        binding.searchView.animate()
//                            .alpha(0f)
//                            .translationY(0f)//-binding.searchView.height.toFloat())
//                            .scaleY(0f)
//                            .setListener(object : AnimatorListenerAdapter() {
//                                override fun onAnimationEnd(animation: Animator?) {
//                                    super.onAnimationEnd(animation)
//                                    binding.searchView.visibility =View.GONE
//                                }
//                            })
//                            .start()
//                    }
//                }
//                else if(dy<0){
//                    scrollDownDistance += dy
//                    val offset = recyclerView.computeVerticalScrollOffset()
//                    if (offset > listHeight/2&& abs(scrollDownDistance)>listHeight) {
//                        binding.searchView.visibility =View.VISIBLE
//                        binding.searchView.scaleX=1f
//                        binding.searchView.scaleY=1f
//                        binding.searchView.alpha=1f
//
//                    }
//                }
//            }
//        })

        initFirstPage()
        initView()
        initData()
        return binding.root
    }

    override fun onDestroy() {
        super.onDestroy()
        EventBusUtil.unregister(this)
    }

//    private fun setTopBackground() {
//
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//            binding.ArticleRecyclerView.setBackgroundColor(Color.argb(statusAlpha, 255, 255, 255))
//            val window = activity!!.window
//            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
//            window.statusBarColor = Color.argb(statusAlpha, 255, 255, 255)
//        }
//    }


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
                    emitter.onNext(item)
                }
                emitter.onComplete()
            })
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .subscribe { t ->
                    if (database.get(t.id) != null) {
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