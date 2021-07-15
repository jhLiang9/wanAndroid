package com.example.wanandroid.activity

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.ViewModelProvider
import com.example.wanandroid.R
import com.example.wanandroid.fragment.*
import com.example.wanandroid.viewmodel.ProjectViewModel
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.bottomnavigation.LabelVisibilityMode
//import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    override fun onDestroy() {
        super.onDestroy()
        Log.d("MainActivity","destory")
    }



//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//
//        setContentView(R.layout.activity_main)
////        var shareModel = ViewModelProviders.of(this).get(SharedViewModel::class.java)  // deprecated
//        var shareModel = ViewModelProvider(this).get(ProjectViewModel::class.java)
//
//        //解决底部导航超过三个，无法显示文字的问题
//        nav_view.labelVisibilityMode= LabelVisibilityMode.LABEL_VISIBILITY_LABELED
//        //初始页面
//        nav_view.setSelectedItemId(R.id.item_news)
//        var start: FragmentTransaction = supportFragmentManager.beginTransaction()
//        start.replace(R.id.home_fragment,HomePageFragment()).commit()
//
//        supportFragmentManager.executePendingTransactions()
//
//        nav_view.setOnNavigationItemSelectedListener (
//
//            BottomNavigationView.OnNavigationItemSelectedListener {
//                var fragmentTransaction: FragmentTransaction = supportFragmentManager.beginTransaction()
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
//                    //发现 、 关注
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
//
//
//
//}
}