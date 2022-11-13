package com.example.wanandroid.activity.baseactivity

import android.graphics.Color
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.wanandroid.service.AppService
import com.example.wanandroid.service.ServiceCreator

open class BaseActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        window.statusBarColor = Color.TRANSPARENT
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR;
    }

    fun <T : ViewModel> getViewModel(modelClass: Class<T>): T =
        ViewModelProvider(this).get(modelClass)

    protected val appService = ServiceCreator.create(AppService::class.java)
    protected fun replaceFragment(id: Int, fragment: Fragment) {
        supportFragmentManager.beginTransaction().replace(id, fragment).commitAllowingStateLoss()
    }

    protected fun addFragment(id: Int, fragment: Fragment) {
        supportFragmentManager.beginTransaction().add(id, fragment).commitAllowingStateLoss()
    }

    protected fun addFragment(id: Int, fragment: Fragment, tag: String) {
        supportFragmentManager.beginTransaction().add(id, fragment, tag).commitAllowingStateLoss()
    }
}