package com.example.wanandroid.fragment


import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.wanandroid.R
import com.example.wanandroid.adapter.ProjectNavAdapter
import com.example.wanandroid.databinding.FragmentProjectListBinding
import com.example.wanandroid.entity.Project
import com.example.wanandroid.viewmodel.ProjectViewModel
import okhttp3.*
import org.json.JSONArray
import org.json.JSONObject
import java.io.IOException



class ProjectListFragment : Fragment() {

    private val navList = ArrayList<Project>()
    //委托
    private val viewModel: ProjectViewModel by activityViewModels()

    private val client = OkHttpClient()

    private lateinit var binding: FragmentProjectListBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_project_list, container, false)
        //线
        binding.nav.addItemDecoration(DividerItemDecoration(activity, DividerItemDecoration.VERTICAL))

        initProjectNavigation()
        //初始化导航内容
        binding.nav.layoutManager = LinearLayoutManager(activity)
        binding.nav.adapter = ProjectNavAdapter(viewModel, navList)
        return binding.root
    }


    private fun initProjectNavigation() {
        val url = "https://www.wanandroid.com/project/tree/json"
        val request = Request.Builder()
            .url(url)
            .build()
        val call: Call = client.newCall(request)
        call.enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                Log.e("OkHttp on fail", "ProjectListFragment")
                e.printStackTrace()
            }

            override fun onResponse(call: Call, response: Response) {
                val responseData = response.body!!.string()
                val jsonData = JSONObject(responseData).getString("data")
                val jsonArray = JSONArray(jsonData)
                for (i in 0 until jsonArray.length()) {
                    val jsonObject = jsonArray.getJSONObject(i)
                    val id = jsonObject.getInt("id")
                    val name = jsonObject.getString("name")
                    navList.add(Project(null, null, id, name, null, null, null, null))
                }
            }
        })
    }

}