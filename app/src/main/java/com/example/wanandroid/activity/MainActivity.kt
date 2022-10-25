package com.example.wanandroid.activity

import android.os.Bundle
import android.view.KeyEvent
import android.view.Menu
import androidx.databinding.DataBindingUtil
import com.example.wanandroid.R
import com.example.wanandroid.activity.baseactivity.BaseActivity
import com.example.wanandroid.databinding.ActivityMainBinding
import com.example.wanandroid.fragment.*
import com.example.wanandroid.utils.ToastUtils
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlin.system.exitProcess

class MainActivity : BaseActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        replaceFragment(R.id.activity_fragment_container, HomepageFragment.newInstance())

        binding.navigation.labelVisibilityMode = BottomNavigationView.LABEL_VISIBILITY_LABELED
        binding.navigation.setOnNavigationItemSelectedListener(
            BottomNavigationView.OnNavigationItemSelectedListener {
                when (it.itemId) {
                    //首页
                    R.id.item_news -> replaceFragment(R.id.activity_fragment_container, HomepageFragment.newInstance())
                    //互动
                    R.id.item_interact -> replaceFragment(R.id.activity_fragment_container, InteractionFragment.newInstance())
                    //体系
                    R.id.item_system -> replaceFragment(R.id.activity_fragment_container, SystemFragment())
                    //项目
                    R.id.item_project -> replaceFragment(R.id.activity_fragment_container, StudyFragment())
                    //个人主页
                    R.id.item_profile -> replaceFragment(R.id.activity_fragment_container, ProfileFragment())
                }
                true
            }
        )
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_toolbar, menu)
        return true
    }

    private var firstTime: Long = 0
    private var secondTime: Long = 0

    override fun onKeyUp(keyCode: Int, event: KeyEvent?): Boolean {
        when (keyCode) {
            KeyEvent.KEYCODE_BACK -> {
                secondTime = System.currentTimeMillis()
                if (secondTime - firstTime > 1000) {
                    ToastUtils.showToast(this, "再按一次返回退出程序")
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