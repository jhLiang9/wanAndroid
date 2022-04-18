package com.example.wanandroid.activity

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.util.SparseArray
import android.view.KeyEvent
import android.view.View
import android.view.Window
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.wanandroid.R
import com.example.wanandroid.databinding.ActivityMainBinding
import com.example.wanandroid.entity.User
import com.example.wanandroid.event.refresh.HomepageGoUpEvent
import com.example.wanandroid.event.refresh.ProjectRefreshEvent
import com.example.wanandroid.event.refresh.QARefreshEvent
import com.example.wanandroid.event.refresh.SystemRefreshEvent
import com.example.wanandroid.fragment.*
import com.example.wanandroid.utils.EventBusUtil
import com.example.wanandroid.viewmodel.ProjectViewModel
import com.example.wanandroid.viewmodel.UserViewModel
import com.google.android.material.tabs.TabLayoutMediator
import org.greenrobot.eventbus.EventBus
import com.github.moduth.blockcanary.BlockCanaryContext

import com.github.moduth.blockcanary.BlockCanary
import com.google.android.material.bottomnavigation.BottomNavigationView
//import android.R
import android.view.Menu
import kotlin.system.exitProcess
import okhttp3.Cookie
import okhttp3.HttpUrl

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.statusBarColor = Color.TRANSPARENT
        window.decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR)

        //BlockCanary
//        BlockCanary.install(this, BlockCanaryContext()).start()

        //hide the title bar
        supportActionBar?.hide()
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root

        val start: FragmentTransaction = supportFragmentManager.beginTransaction()
        start.replace(R.id.activity_fragment_container, HomePageFragment()).commit()
        supportFragmentManager.executePendingTransactions()

        binding.navigation.labelVisibilityMode = BottomNavigationView.LABEL_VISIBILITY_LABELED
        binding.navigation.setOnNavigationItemSelectedListener(
            BottomNavigationView.OnNavigationItemSelectedListener {
                val fragmentTransaction: FragmentTransaction =
                    supportFragmentManager.beginTransaction()
                when (it.itemId) {
                    //首页
                    R.id.item_news -> {
                        fragmentTransaction.replace(
                            R.id.activity_fragment_container,
                            HomePageFragment.newInstance()
                        ).commit()
                        return@OnNavigationItemSelectedListener true
                    }
                    //互动
                    R.id.item_interact -> {
                        fragmentTransaction.replace(
                            R.id.activity_fragment_container,
                            InteractionFragment.newInstance()
                        ).commit()
                        return@OnNavigationItemSelectedListener true
                    }
                    //体系
                    R.id.item_system -> {
                        fragmentTransaction.replace(
                            R.id.activity_fragment_container,
                            SystemFragment()
                        ).commit()
//                        addToBackStack就是 加入到回退栈。取决于你是否要在回退的时候显示上一个Fragment。
//                        fragmentTransaction.add(R.id.activity_fragment_container,ProfileFragment()).addToBackStack("ProfileFragment").commit();
                        return@OnNavigationItemSelectedListener true
                    }
                    //项目
                    R.id.item_project -> {
                        fragmentTransaction.replace(
                            R.id.activity_fragment_container,
                            StudyFragment()
                        ).commit()
                        return@OnNavigationItemSelectedListener true
                    }
                    //个人主页
                    R.id.item_profile -> {
                        fragmentTransaction.replace(
                            R.id.activity_fragment_container,
                            ProfileFragment()
                        ).commit()
                        return@OnNavigationItemSelectedListener true
                    }
                }
                true
            }
        )
        setContentView(view)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(com.example.wanandroid.R.menu.menu_toolbar, menu)
        return true
    }

    private var firstTime: Long = 0
    private var secondTime: Long = 0
    override fun onKeyUp(keyCode: Int, event: KeyEvent?): Boolean {
        when (keyCode) {
            KeyEvent.KEYCODE_BACK -> {
                secondTime = System.currentTimeMillis()
                if (secondTime - firstTime > 2000) {
                    Toast.makeText(this, "再按一次返回退出程序", Toast.LENGTH_SHORT).show()
                    firstTime = secondTime
                } else {
                    finish()
                    exitProcess(0)
                }
                return true
            }
        }
        return super.onKeyUp(keyCode, event)
    }

}