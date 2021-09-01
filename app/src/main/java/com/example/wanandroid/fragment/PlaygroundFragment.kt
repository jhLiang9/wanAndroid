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
import androidx.recyclerview.widget.RecyclerView.Adapter
import com.example.wanandroid.R
import com.example.wanandroid.activity.WebViewActivity
import com.example.wanandroid.databinding.FragmentPlaygroundBinding
import com.example.wanandroid.entity.Article
import com.example.wanandroid.viewmodel.PlaygroundViewModel

class PlaygroundFragment : Fragment() {

    companion object {
        fun newInstance() = PlaygroundFragment()
    }

    private lateinit var viewModel: PlaygroundViewModel
    private lateinit var binding: FragmentPlaygroundBinding
    private val list = ArrayList<Article>()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_playground, container, false)
        viewModel = ViewModelProvider(this).get(PlaygroundViewModel::class.java)
        init()
        binding.recyclerView.layoutManager   = LinearLayoutManager(context)
        binding.recyclerView.adapter = PlayGroundAdapter(list,viewModel)
        viewModel.getList().observe(viewLifecycleOwner,{
            list.addAll(it)
            binding.recyclerView.adapter?.notifyDataSetChanged()
        })

        return binding.root
    }

    private fun init() {
        viewModel.getData(0)
    }


}
class PlayGroundAdapter(val list:ArrayList<Article>,val viewModel: PlaygroundViewModel): Adapter<PlayGroundAdapter.ViewHolder>(){

    inner class ViewHolder(view: View) :RecyclerView.ViewHolder(view){
        val title: TextView = view.findViewById(R.id.title)
        val author: TextView = view.findViewById(R.id.author)
        val time: TextView = view.findViewById(R.id.time)
        val superChapterName: TextView =view.findViewById(R.id.superChapterName)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_article, parent, false)
        val viewHolder=ViewHolder(view)
        viewHolder.itemView.setOnClickListener {
            val position = viewHolder.bindingAdapterPosition //获取用户点击的position
            val article = list.get(position)
            val link :String= article.link
            val intent = Intent(parent.context, WebViewActivity::class.java)
            intent.putExtra("data", link);
            parent.context.startActivity(intent)
        }
        return viewHolder

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val article = list.get(position)
        holder.title.text= article.title
        holder.author.text=article.author
        holder.superChapterName.text=article.superChapterName
        holder.time.text=article.niceDate
        //加载下一页
        if(position == itemCount-5){
            getNextPage()
        }
    }

    private fun getNextPage() = viewModel.getData(viewModel.currentPage++)

    override fun getItemCount(): Int  = list.size
}