package com.example.wanandroid.activity

import android.os.Bundle
import android.util.SparseArray
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.wanandroid.R
import com.example.wanandroid.databinding.ActivityMainBinding
import com.example.wanandroid.event.refresh.HomepageGoUpEvent
import com.example.wanandroid.event.refresh.ProjectRefreshEvent
import com.example.wanandroid.event.refresh.QARefreshEvent
import com.example.wanandroid.event.refresh.SystemRefreshEvent
import com.example.wanandroid.fragment.*
import com.example.wanandroid.utils.EventBusUtil
import com.example.wanandroid.viewmodel.ProjectViewModel
import com.google.android.material.tabs.TabLayoutMediator
import org.greenrobot.eventbus.EventBus
import com.github.moduth.blockcanary.BlockCanaryContext

import com.github.moduth.blockcanary.BlockCanary
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.bottomnavigation.LabelVisibilityMode

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val sparseArray: SparseArray<Fragment> = SparseArray(5)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //cache
        val userConfig = getSharedPreferences("user",0)
        val name = userConfig.getString("user_name","")
        if(name==""){
            //....
        }

        //BlockCanary
//        BlockCanary.install(this, BlockCanaryContext()).start()
        //hide the title bar
        supportActionBar?.hide()
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root

//        val tabs= listOf("首页","体系","问答","项目","我的")
//        val icons = listOf(R.drawable.homepage,R.drawable.system,R.drawable.find,R.drawable.projects,R.drawable.user)
        sparseArray.put(0, HomePageFragment())
        sparseArray.put(1, SystemFragment())
        sparseArray.put(2, QuestionAndAnswerFragment())
        sparseArray.put(3, ProjectFragment())
        sparseArray.put(4, ProfileFragment())

//        binding.viewPager.adapter = object : FragmentStateAdapter(this) {
//            override fun getItemCount(): Int = 5
//            //TODO 判断是否存活，存活无须多次创建
//            override fun createFragment(position: Int): Fragment = sparseArray.get(position)
//        }

//        TabLayoutMediator(binding.tabs, binding.viewPager) { tab, position ->
//            tab.text = tabs[position]
//            tab.icon = ResourcesCompat.getDrawable(resources,icons[position],null)
//            tab.view.setOnClickListener {
////                binding.viewPager.setCurrentItem(position)
//                if(tab.isSelected){
//                    when(position){
//                        0->EventBusUtil.post(HomepageGoUpEvent)
//                        1->EventBusUtil.post(SystemRefreshEvent)
//                        2->EventBusUtil.post(QARefreshEvent)
//                        3->EventBusUtil.post(ProjectRefreshEvent)
//                    }
//                }
//            }
//        }.attach()
        val start: FragmentTransaction = supportFragmentManager.beginTransaction()
        start.replace(R.id.activity_fragment_container,HomePageFragment()).commit()
        supportFragmentManager.executePendingTransactions()

        binding.navigation.labelVisibilityMode= BottomNavigationView.LABEL_VISIBILITY_LABELED
        binding.navigation.setOnNavigationItemSelectedListener (
            BottomNavigationView.OnNavigationItemSelectedListener {
                val fragmentTransaction: FragmentTransaction = supportFragmentManager.beginTransaction()
                when(it.itemId){
                    //首页
                    R.id.item_news->{
                        fragmentTransaction.replace(R.id.activity_fragment_container, HomePageFragment()).commit()
                        return@OnNavigationItemSelectedListener true
                    }
                    //体系
                    R.id.item_lib->{
                        fragmentTransaction.replace(R.id.activity_fragment_container, SystemFragment()).commit()
                        return@OnNavigationItemSelectedListener true
                    }
                    //问答
                    R.id.item_qa->{
                        fragmentTransaction.replace(R.id.activity_fragment_container,QuestionAndAnswerFragment()).commit()
//                        addToBackStack就是 加入到回退栈。取决于你是否要在回退的时候显示上一个Fragment。
//                        fragmentTransaction.add(R.id.activity_fragment_container,ProfileFragment()).addToBackStack("ProfileFragment").commit();
                        return@OnNavigationItemSelectedListener true
                    }
                    //项目
                    R.id.item_project->{
                        fragmentTransaction.replace(R.id.activity_fragment_container, ProjectFragment()).commit()
                        return@OnNavigationItemSelectedListener true
                    }
                    //个人主页
                    R.id.item_profile->{
                        fragmentTransaction.replace(R.id.activity_fragment_container, ProfileFragment()).commit()
                        return@OnNavigationItemSelectedListener true
                    }
                }
                true
            }
        )
        setContentView(view)
    }
}