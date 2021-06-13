package com.example.wanandroid.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.wanandroid.R
import com.example.wanandroid.adapter.HomeArticleAdapter
import com.example.wanandroid.entity.Article
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_project.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [ProjectFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ProjectFragment : Fragment() {
    // TODO: Rename and change types of parameters

    private val articleList = ArrayList<Article>()

    private var param1: String? = null
    private var param2: String? = null

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initArticles()
        val layoutManager = LinearLayoutManager(activity)
        TestRecyclerView.layoutManager = layoutManager
        val adapter = HomeArticleAdapter(articleList)
        TestRecyclerView.adapter = adapter
    }

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val root= inflater.inflate(R.layout.fragment_project, container, false)
//        initArticles()
//        val recyclerView = root.findViewById<RecyclerView>(R.id.ArticleRecyclerView)
//
//        val layoutManager = LinearLayoutManager(activity)
//        recyclerView.layoutManager=layoutManager
        //ArticleRecyclerView.layoutManager = layoutManager
//        val adapter = HomeArticleAdapter(articleList)
//        ArticleRecyclerView.adapter = adapter
//        recyclerView.adapter=adapter
        return inflater.inflate(R.layout.fragment_project, container, false)
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment ProjectFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            ProjectFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }

    }

    private fun initArticles() {
        repeat(2) {
            articleList.add(Article("Apple", "a","sa","sd"))
            articleList.add(Article("Apple", "a","sa","sd"))
            articleList.add(Article("Apple", "a","sa","sd"))
            articleList.add(Article("Apple", "a","sa","sd"))
            articleList.add(Article("Apple", "a","sa","sd"))
        }
    }

}