package com.example.wanandroid.fragment

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.wanandroid.R
import com.example.wanandroid.activity.WebViewActivity
import com.example.wanandroid.databinding.FragmentWxArticleBinding
import com.example.wanandroid.entity.Article
import com.example.wanandroid.entity.ArticleList
import com.example.wanandroid.fragment.basefragment.BaseFragment
import com.example.wanandroid.viewmodel.WxArticleViewModel
import io.reactivex.rxjava3.core.Single

private const val ARG_ID = "wxId"
private const val ARG_PAGE = "page"

/**
 * 微信公众号文章Fragment
 */
class WxArticleFragment : BaseFragment() {
    private var wxId: Int = 0
    private var page: Int = 0
    private val articleList = ArrayList<Article>()
    private lateinit var binding: FragmentWxArticleBinding
    private lateinit var viewModel: WxArticleViewModel

    companion object {
        /**
         *
         * @param id 公众号id
         * @param page 页码
         * @return A new instance of fragment WxArticleFragment.
         */
        @JvmStatic
        fun newInstance(id: Int, page: Int) =
            WxArticleFragment().apply {
                arguments = Bundle().apply {
                    putInt(ARG_ID, id)
                    putInt(ARG_PAGE, page)
                }
            }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            wxId = it.getInt(ARG_ID)
            page = it.getInt(ARG_PAGE)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_wx_article, container, false)
        viewModel = getViewModel(WxArticleViewModel::class.java)
        init()
        binding.recyclerView.layoutManager = LinearLayoutManager(context)
        binding.recyclerView.adapter = WxArticleAdapter(articleList)
        viewModel.getArticleList().observe(viewLifecycleOwner, {
            binding.loadingPanel.visibility=View.GONE
            articleList.addAll(it.data.datas)
            binding.recyclerView.adapter?.notifyDataSetChanged()
        })

        return binding.root
    }

    private fun init() {
        viewModel.getArticles(wxId, page)

    }

}

class WxArticleAdapter(val list: ArrayList<Article>) :
    RecyclerView.Adapter<WxArticleAdapter.ViewHolder>() {
    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val title: TextView = view.findViewById(R.id.title)
        val author: TextView = view.findViewById(R.id.author)
        val time: TextView = view.findViewById(R.id.time)
        val superChapterName: TextView = view.findViewById(R.id.superChapterName)
    }

    override fun getItemCount(): Int = list.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_article, parent, false)
        val viewHolder = ViewHolder(view)
        viewHolder.itemView.setOnClickListener {
            val position = viewHolder.bindingAdapterPosition //获取用户点击的position
            val article = list[position]
            val link: String = article.link
            val intent = Intent(parent.context, WebViewActivity::class.java)
            intent.putExtra("data", link);
            parent.context.startActivity(intent)
        }

        return viewHolder

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val article = list[position]
        holder.title.text = article.title
        holder.author.text = article.author
        holder.superChapterName.text = article.superChapterName
        holder.time.text = article.niceDate
        if (position == itemCount - 5) {
//            getNextPage()
        }
    }
}
