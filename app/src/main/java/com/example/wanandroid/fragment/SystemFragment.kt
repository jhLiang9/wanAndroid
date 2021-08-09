package com.example.wanandroid.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.wanandroid.R
import com.example.wanandroid.adapter.SystemAdapter
import com.example.wanandroid.databinding.FragmentSystemBinding

import com.example.wanandroid.entity.System

import okhttp3.OkHttpClient
import okhttp3.Request
import org.json.JSONArray
import org.json.JSONObject
import kotlin.concurrent.thread

/**
 * ViewModel for ProjectFragment
 */


class SystemFragment : Fragment() {
    var datalist= ArrayList<ArrayList<System>>()
    var sublist =ArrayList<System>()
    var titleList =ArrayList<String>()

    private lateinit var binding:FragmentSystemBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        initLayoutData()
        binding =DataBindingUtil.inflate(inflater,R.layout.fragment_system,container,false)
        val layoutManager = LinearLayoutManager(activity)
        binding.systemList.layoutManager = layoutManager
        val listAdapter = SystemAdapter(titleList)
        binding.systemList.adapter = listAdapter
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
    }


    private fun initLayoutData(){
        val url="https://www.wanandroid.com/tree/json"
        thread {
            val client= OkHttpClient()
            val request = Request.Builder()
                .url(url)
                //.url("https://www.wanandroid.com/article/list/0/json")
                .build()
            val response = client.newCall(request).execute()
            val responseData = response.body?.string()
            val jsondata= JSONObject(responseData).getString("data")

            try {
                val jsonArray = JSONArray(jsondata)
                for (i in 0 until jsonArray.length()) {
                    val jsonObject = jsonArray.getJSONObject(i)

                    val children= JSONArray(jsonObject.getString("children"))
                    val parent_name=jsonObject.getString("name")
                    //TODO: title list应该有id，章节名称，或者link
                    titleList.add(parent_name.toString())
//                    for(j in 0 until children.length()){
//                        val temp=children.getJSONObject(j)
//                        val courseId=temp.getInt("courseId")
//                        val id =temp.getInt("id")
//                        val name =temp.getString("name")
//
//                        val order=temp.getInt("order")
//                        val parentChapterId=temp.getInt("parentChapterId")
//                        val userControlSetTop=temp.getBoolean("userControlSetTop")
//                        val visible=temp.getInt("visible")
//                       sublist.add(System(null,courseId,id,name,order,parentChapterId,userControlSetTop,visible)) //内层 children为null
//                    }
//                    datalist.add(sublist)
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }




}