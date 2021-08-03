package com.example.wanandroid.activity

import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.wanandroid.R
import com.example.wanandroid.databinding.ActivityMainBinding
import com.example.wanandroid.fragment.*
import com.example.wanandroid.viewmodel.ProjectViewModel
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.bottomnavigation.LabelVisibilityMode



class MainActivity : AppCompatActivity() {

    private lateinit var binding:ActivityMainBinding
    private val viewModel :ProjectViewModel by viewModels()


    override fun onDestroy() {
        super.onDestroy()
        Log.d("MainActivity","destroy")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        //hide the title bar
        supportActionBar?.hide()

        Log.d("MainActivity","initialize")

        //init binding
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view =binding.root
        Log.d("MainActivity","binding init")
        //解决底部导航超过三个，无法显示文字的问题
        binding.navView.labelVisibilityMode= LabelVisibilityMode.LABEL_VISIBILITY_LABELED

        //初始页面
        binding.navView.setSelectedItemId(R.id.item_news)
        binding.viewPager.adapter = object : FragmentStateAdapter(this){
            override fun getItemCount(): Int =5

            override fun createFragment(position: Int): Fragment {
                return when (position) {
                    1 ->  HomePageFragment()
                    2 ->  SystemFragment()
                    3 ->  QuestionAndAnswerFragment()
                    4 ->  ProjectFragment()
                    else -> ProfileFragment()
                }
            }

        }



//        val start: FragmentTransaction = supportFragmentManager.beginTransaction()
//        start.replace(R.id.home_fragment,HomePageFragment()).commit()
//        supportFragmentManager.executePendingTransactions()
//        binding.navView.setOnNavigationItemSelectedListener (
//
//            BottomNavigationView.OnNavigationItemSelectedListener {
//                val fragmentTransaction: FragmentTransaction = supportFragmentManager.beginTransaction()
//                when(it.itemId){
//                    //首页
//                    R.id.item_news->{
//                        fragmentTransaction.replace(R.id.home_fragment, HomePageFragment()).commit()
//                        return@OnNavigationItemSelectedListener true
//                        //viewpager.setCurrentItem(0)
//                       // return  @OnNavigationItemSelectedListener true
//                    }
//                    //体系
//                    R.id.item_lib->{
//                        fragmentTransaction.replace(R.id.home_fragment, SystemFragment()).commit()
//                        return@OnNavigationItemSelectedListener true
////                        viewpager.setCurrentItem(1)
//                    }
//                    //问答
//                    R.id.item_find->{
//                        fragmentTransaction.replace(R.id.home_fragment,QuestionAndAnswerFragment()).commit()
//                        return@OnNavigationItemSelectedListener true
////                        viewpager.setCurrentItem(2)
//                    }
//                    //项目
//                    R.id.item_project->{
//                        fragmentTransaction.replace(R.id.home_fragment, ProjectFragment()).commit()
//                        return@OnNavigationItemSelectedListener true
//                    }
//                    //个人主页
//                    R.id.item_more->{
//                        fragmentTransaction.replace(R.id.home_fragment, ProfileFragment()).commit()
//                        return@OnNavigationItemSelectedListener true
////                        viewpager.setCurrentItem(4)
//
//                    }
//                }
//                true
//            }
//        )

        setContentView(view)

    }
}