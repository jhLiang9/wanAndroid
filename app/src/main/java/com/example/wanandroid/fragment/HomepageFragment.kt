package com.example.wanandroid.fragment

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Rect
import android.os.Bundle
import android.util.Log
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
import com.example.wanandroid.adapter.viewholder.ArticleViewHolder
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
    private val adapter by lazy { HomeArticleAdapter(viewModel) }

    companion object {
        fun newInstance() = HomepageFragment()
        fun newInstance(bundle: Bundle): HomepageFragment {
            val fragment = HomepageFragment()
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


    /**
     * ?????????????????????????????????
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onEvent(event: BackToTopEvent) {
        binding.recyclerView.scrollY = 0
        binding.recyclerView.smoothScrollToPosition(0)
    }

    private fun initView() {
        binding.searchView.setOnClickListener {
            val intent = Intent(context, SearchActivity::class.java)
            startActivity(intent)
        }
        binding.searchBar.setOnClickListener {
            val intent = Intent(context, SearchActivity::class.java)
            startActivity(intent)
        }

        var scrollDownDistance = 0
        var scrollUpDistance = 0

        binding.recyclerView.layoutManager = LinearLayoutManager(activity)
        binding.recyclerView.adapter = adapter
        binding.recyclerView.addItemDecoration(object : RecyclerView.ItemDecoration() {
            override fun onDraw(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {
                super.onDraw(c, parent, state)
                val left: Int = parent.paddingLeft
                val right: Int = parent.width - parent.paddingRight
                val childCount: Int = parent.getChildCount()
                for (i in 0 until childCount) {
                    val child: View = parent.getChildAt(i)
                    val holder = parent.getChildViewHolder(child)

                    if (holder is ArticleViewHolder) {
                        Log.i("ljh", adapter.getItemViewType(i).toString())
                    }
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

            override fun getItemOffsets(
                outRect: Rect,
                view: View,
                parent: RecyclerView,
                state: RecyclerView.State
            ) {
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
                //????????????
                val temp = ViewConfiguration.get(context).scaledTouchSlop
                val listHeight = recyclerView.height
                if (dy > 10) {
                    if (abs(dy) > temp) {
                        scrollDownDistance = 0
                    }
                    //????????????
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
                    //????????????
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

    /**
     * ?????????????????????????????????
     */
    private fun initData() {

        viewModel.articleList.observe(viewLifecycleOwner) {
            it?.let {
                adapter.appendData(it.datas)
                viewModel.pageCount = it.pageCount
            }
            binding.loadingPanel.visibility = View.GONE
        }
    }


    /**
     * ??????????????????
     */
    private fun initFirstPage() = viewModel.getFirstPage()


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