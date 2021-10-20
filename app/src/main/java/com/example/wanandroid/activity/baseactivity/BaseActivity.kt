package com.example.wanandroid.activity.baseactivity

import android.os.Bundle
import android.os.PersistableBundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.wanandroid.R
import com.example.wanandroid.fragment.MyCollectionFragment
import com.example.wanandroid.service.AppService
import com.example.wanandroid.service.ServiceCreator
import com.example.wanandroid.viewmodel.SearchViewModel
import com.example.wanandroid.viewmodel.baseviewmodel.BaseViewModel

open class BaseActivity : AppCompatActivity(){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
    }

    fun <T : ViewModel> getViewModel(modelClass: Class<T>): T  = ViewModelProvider(this).get(modelClass)
    protected val appService = ServiceCreator.create(AppService::class.java)
    private val fragmentTransaction: FragmentTransaction = supportFragmentManager.beginTransaction()
    protected fun replaceFragment(id:Int,fragment: Fragment){
        fragmentTransaction.replace(id,fragment).commit()
    }
    protected fun addFragment(id:Int,fragment: Fragment){
        fragmentTransaction.add(id,fragment).commit()
    }
    protected fun addFragment(id:Int,fragment: Fragment,tag:String){
        fragmentTransaction.add(id,fragment,tag)
    }
}