package com.example.wanandroid.fragment

import android.animation.Animator
import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
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
import io.reactivex.rxjava3.schedulers.Schedulers
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import org.reactivestreams.Subscriber
import org.reactivestreams.Subscription
import kotlin.concurrent.thread
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import android.animation.AnimatorListenerAdapter
import android.content.Intent
import android.graphics.Color
import android.os.Build
import android.view.*
import androidx.core.widget.NestedScrollView
import com.example.wanandroid.activity.SearchActivity
import com.google.android.material.appbar.AppBarLayout
import kotlin.math.abs


class HomePageFragment : HomePageFragmentVM() {
    var statusAlpha = 0
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
        binding.searchView.setOnClickListener {
            val intent = Intent(context, SearchActivity::class.java)
            startActivity(intent)
        }
        binding.searchBar.setOnClickListener {
            val intent = Intent(context, SearchActivity::class.java)
            startActivity(intent)
        }
        database =
            ArticleDatabase.getInstance(requireContext().applicationContext).articleDatabaseDao

        var scrollDownDistance = 0
        var scrollUpDistance = 0
        binding.ArticleRecyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                //正在滚动
//                public static final int SCROLL_STATE_IDLE = 0;

                //正在被外部拖拽,一般为用户正在用手指滚动
//                public static final int SCROLL_STATE_DRAGGING = 1;
                if (newState == RecyclerView.SCROLL_STATE_DRAGGING) {
                    var offset: Int = recyclerView.computeVerticalScrollOffset();
                    Log.i("roll", offset.toString())
//                    if (offset <500) {
//                        //根据是否select 是否显示
//                        binding.searchBar.setSelected(true);
//                    }
                }

                //自动滚动开始
//                public static final int SCROLL_STATE_SETTLING = 2;
                super.onScrollStateChanged(recyclerView, newState)
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    var offset: Int = recyclerView.computeVerticalScrollOffset();

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
                        return;
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
                        return;
                    }
                    //向上滚动
                    scrollDownDistance += dy
//                    var offset = recyclerView.computeVerticalScrollOffset()
//                    if (offset > listHeight * 2&& abs(scrollDownDistance)>listHeight) {}
                    showSearch()
                }
            }
        })

        initFirstPage()
        initView()
        initData()
        return binding.root
    }

    private fun showSearch() {
        binding.searchBar.visibility = View.VISIBLE
        binding.searchBar.translationY = -binding.searchBar.height.toFloat()
        binding.searchBar.alpha = 0f
        binding.searchBar.scaleY = 0f
        binding.searchBar.animate()
            .alpha(1f)
            .translationY(0f)
            .scaleY(1.0f)
            .setListener(null)
            .start()


    }

    private fun hideSearch() {
        binding.searchBar.alpha = 1f
        binding.searchBar.scaleX = 1f
        binding.searchBar.scaleY = 1f
        binding.toolbar.animate()
            .translationY(0f)
            .setDuration(200L)
            .start()
        binding.searchBar.animate()
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