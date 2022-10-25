package com.example.wanandroid.fragment

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.annotation.SuppressLint
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Rect
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewConfiguration
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.wanandroid.R
import com.example.wanandroid.activity.SearchActivity
import com.example.wanandroid.adapter.ArticleAdapter
import com.example.wanandroid.databinding.FragmentHomePageBinding
import com.example.wanandroid.event.refresh.BackToTopEvent
import com.example.wanandroid.fragment.basefragment.BaseFragment
import com.example.wanandroid.utils.EventBusUtil
import com.example.wanandroid.viewmodel.HomePageViewModel
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import kotlin.math.abs


class HomepageFragment : BaseFragment() {
    private val viewModel by lazy { getViewModel(HomePageViewModel::class.java) }
    private lateinit var binding: FragmentHomePageBinding
    private val adapter by lazy { ArticleAdapter(viewModel) }

    companion object {
        fun newInstance() = HomepageFragment()
        fun newInstance(bundle: Bundle): HomepageFragment {
            val fragment = HomepageFragment()
            fragment.arguments = bundle
            return fragment
        }

    }


    @SuppressLint("NotifyDataSetChanged")
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        EventBusUtil.register(this)
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_home_page, container, false)
        initView()
        initData()
        return binding.root
    }


    /**
     * 点击下方导航栏回到顶部
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onEvent(event: BackToTopEvent) {
        binding.recyclerView.scrollY = 0
        binding.recyclerView.smoothScrollToPosition(0)
    }

    private fun initView() {
        binding.searchView.setOnClickListener {
            SearchActivity.start(context)
        }
        binding.searchBar.setOnClickListener {
            SearchActivity.start(context)
        }

        var scrollDownDistance = 0
        var scrollUpDistance = 0

        binding.recyclerView.adapter = adapter
        binding.recyclerView.addItemDecoration(object : RecyclerView.ItemDecoration() {
            override fun onDraw(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {
                super.onDraw(c, parent, state)
                val left: Int = parent.paddingLeft
                val right: Int = parent.width - parent.paddingRight
                val childCount: Int = parent.getChildCount()
                for (i in 0 until childCount) {
                    val child: View = parent.getChildAt(i)
                    val rlp = child.layoutParams as RecyclerView.LayoutParams
                    val top = child.bottom + rlp.bottomMargin
                    val bottom: Int = top + 5
                    val paint = Paint()
                    paint.color = resources.getColor(R.color.gray, null)
                    val r = Rect(left, top, right, bottom)
                    c.drawRect(r, paint)
                }

            }

            override fun onDrawOver(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {
                super.onDrawOver(c, parent, state)
            }

            override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
                super.getItemOffsets(outRect, view, parent, state)
                outRect.top = 5
            }
        })
        binding.recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                if (newState == RecyclerView.SCROLL_STATE_DRAGGING) {
                    var offset: Int = recyclerView.computeVerticalScrollOffset()
                }

                super.onScrollStateChanged(recyclerView, newState)
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    val offset: Int = recyclerView.computeVerticalScrollOffset()

                    if (offset == 0) {
//                        binding.searchBar.setSelected(true);
                    }
                }

            }

            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                //向下滚动
                val temp = ViewConfiguration.get(context).scaledTouchSlop
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

        binding.refreshLayout.setOnRefreshListener {
            binding.refreshLayout.isRefreshing = false
            viewModel.refresh()
        }

    }

    private fun initData() {
        viewModel.getFirstPage()
        viewModel.articleList.observe(viewLifecycleOwner) {
            adapter.appendData(it?.datas)
            binding.loadingPanel.visibility = View.GONE
        }
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