package com.example.wanandroid.fragment

import android.content.Intent
import androidx.lifecycle.ViewModelProvider
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
import com.example.wanandroid.databinding.FragmentOfficialAccountBinding
import com.example.wanandroid.entity.Article
import com.example.wanandroid.viewmodel.OfficialAccountViewModel

class OfficialAccountFragment : Fragment() {

    companion object {
        fun newInstance() = OfficialAccountFragment()
    }

    private lateinit var viewModel: OfficialAccountViewModel
    private lateinit var binding: FragmentOfficialAccountBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val list = ArrayList<Article>()
        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_official_account, container, false)
        viewModel = ViewModelProvider(this).get(OfficialAccountViewModel::class.java)
        binding.recyclerView.layoutManager = LinearLayoutManager(context)
        binding.recyclerView.adapter = WXAdapter(list, viewModel)

        viewModel.getList().observe(viewLifecycleOwner, {
            val data = it.data.datas
            list.addAll(data)
        })


        return binding.root
    }
}

class WXAdapter(val list: ArrayList<Article>, val viewModel: OfficialAccountViewModel) :
    RecyclerView.Adapter<WXAdapter.ViewHolder>() {

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val title: TextView = view.findViewById(R.id.title)
        val author: TextView = view.findViewById(R.id.author)
        val time: TextView = view.findViewById(R.id.time)
        val superChapterName: TextView = view.findViewById(R.id.superChapterName)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WXAdapter.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.article_item, parent, false)
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

    override fun onBindViewHolder(holder: WXAdapter.ViewHolder, position: Int) {
        val article = list[position]
        holder.title.text = article.title
        holder.author.text = article.author
        holder.superChapterName.text = article.superChapterName
        holder.time.text = article.niceDate
        if (position == itemCount - 5) {
            getNextPage()
        }
    }

    override fun getItemCount(): Int = list.size
    
    private fun getNextPage() = viewModel.getData(viewModel.currentPage++)


}