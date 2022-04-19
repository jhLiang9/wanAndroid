package com.example.wanandroid.activity


import android.graphics.Color
import android.os.Bundle
import android.view.KeyEvent
import android.view.Menu
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentTransaction
import com.example.wanandroid.R
import com.example.wanandroid.databinding.ActivityMainBinding
import com.example.wanandroid.fragment.*
import com.example.wanandroid.utils.ToastUtils
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlin.system.exitProcess

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.statusBarColor = Color.TRANSPARENT
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR

        //hide the title bar
        supportActionBar?.hide()
        binding = ActivityMainBinding.inflate(layoutInflater)

        supportFragmentManager.beginTransaction()
            .replace(R.id.activity_fragment_container, HomepageFragment()).commit()

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
                            HomepageFragment.newInstance()
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
        setContentView(binding.root)
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
                if (secondTime - firstTime > 2000) {
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