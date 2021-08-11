package com.example.wanandroid.activity

import android.os.Bundle
import android.util.SparseArray
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.wanandroid.R
import com.example.wanandroid.databinding.ActivityMainBinding
import com.example.wanandroid.event.refresh.HomepageRefreshEvent
import com.example.wanandroid.event.refresh.ProjectRefreshEvent
import com.example.wanandroid.event.refresh.QARefreshEvent
import com.example.wanandroid.event.refresh.SystemRefreshEvent
import com.example.wanandroid.fragment.*
import com.example.wanandroid.utils.EventBusUtil
import com.example.wanandroid.viewmodel.ProjectViewModel
import com.google.android.material.tabs.TabLayoutMediator
import org.greenrobot.eventbus.EventBus


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val viewModel: ProjectViewModel by viewModels()
    private val sparseArray: SparseArray<Fragment> = SparseArray(5)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //hide the title bar
        supportActionBar?.hide()
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root

        val tabs= listOf("首页","体系","问答","项目","我的")
        val icons = listOf(R.drawable.homepage,R.drawable.system,R.drawable.find,R.drawable.projects,R.drawable.user)
        sparseArray.put(0, HomePageFragment.getInstance())
        sparseArray.put(1, SystemFragment())
        sparseArray.put(2, QuestionAndAnswerFragment.getInstance())
        sparseArray.put(3, ProjectFragment())
        sparseArray.put(4, ProfileFragment())

        binding.viewPager.adapter = object : FragmentStateAdapter(this) {
            override fun getItemCount(): Int = 5

            override fun createFragment(position: Int): Fragment = sparseArray.get(position)
        }


        TabLayoutMediator(binding.tabs, binding.viewPager) { tab, position ->
            tab.text = tabs[position]
            tab.icon = ResourcesCompat.getDrawable(resources,icons[position],null)
            tab.view.setOnClickListener {
                if(tab.isSelected){
                    when(position){
                        0->EventBusUtil.post(HomepageRefreshEvent)
                        1->EventBusUtil.post(SystemRefreshEvent)
                        2->EventBusUtil.post(QARefreshEvent)
                        3->EventBusUtil.post(ProjectRefreshEvent)
                    }
                }

            }
        }.attach()


        setContentView(view)

    }

}