package com.example.wanandroid.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.wanandroid.R
import com.example.wanandroid.adapter.SystemAdapter
import com.example.wanandroid.adapter.SystemContentAdapter

import com.example.wanandroid.entity.System
import com.google.android.flexbox.FlexboxLayoutManager
import kotlinx.android.synthetic.main.fragment_project_list.*
import kotlinx.android.synthetic.main.fragment_system.*
import kotlinx.android.synthetic.main.fragment_system.system_list
import kotlinx.android.synthetic.main.item_system.*
import okhttp3.OkHttpClient
import okhttp3.Request
import org.json.JSONArray
import org.json.JSONObject
import kotlin.concurrent.thread

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

var datalist= ArrayList<ArrayList<System>>()
var sublist =ArrayList<System>()
var titleList =ArrayList<String>()
/**
 * A simple [Fragment] subclass.
 * Use the [SystemFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class SystemFragment : Fragment() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_system, container, false)
    }



    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initLayoutData()
        Thread.sleep(1000)
        initLayout()
    }



    companion object
    {
        //静态
        @JvmStatic
        fun newInstance(): SystemFragment {
            return SystemFragment()
        }
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
                    titleList.add(parent_name)
                    for(j in 0 until children.length()){
                        val temp=children.getJSONObject(j)
                        val courseId=temp.getInt("courseId")
                        val id =temp.getInt("id")
                        val name =temp.getString("name")

                        val order=temp.getInt("order")
                        val parentChapterId=temp.getInt("parentChapterId")
                        val userControlSetTop=temp.getBoolean("userControlSetTop")
                        val visible=temp.getInt("visible")
                       sublist.add(System(null,courseId,id,name,order,parentChapterId,userControlSetTop,visible)) //内层 children为null
                    }
                    datalist.add(sublist)
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    private fun initLayout(){
        //瀑布布局
        val layoutManager = LinearLayoutManager(activity)
        system_list.layoutManager = layoutManager
        val listAdapter = SystemAdapter(titleList)
        system_list.adapter = listAdapter



//        val contentLayoutManager = StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL)

        system_detail.layoutManager = LinearLayoutManager(activity)
        val detialAdapter = SystemContentAdapter(sublist) //TODO:maybe some problem
        system_detail.adapter = detialAdapter

    }


}