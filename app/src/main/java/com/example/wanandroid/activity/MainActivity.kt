package com.example.wanandroid.activity

import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentTransaction
import com.example.wanandroid.R
import com.example.wanandroid.fragment.HomePageFragment
import com.example.wanandroid.fragment.ProjectListFragment
import com.example.wanandroid.fragment.SystemFragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    override fun onDestroy() {
        super.onDestroy()
        Log.d("Activity:","destory")
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)

        nav_view.setSelectedItemId(R.id.item_news)
        //默认 >3 的选中效果会影响ViewPager的滑动切换时的效果，故利用反射去掉
//        BottomNavigationViewHelper.disableShiftMode(bottomNavigationView);


        nav_view.setOnNavigationItemSelectedListener (
            BottomNavigationView.OnNavigationItemSelectedListener {
                var fragmentTransaction: FragmentTransaction = supportFragmentManager.beginTransaction()
                when(it.itemId){
                    R.id.item_news->{
                        fragmentTransaction.replace(R.id.home_fragment, HomePageFragment()).commit()
                        return@OnNavigationItemSelectedListener true
                        //                        viewpager.setCurrentItem(0)
                       // return  @OnNavigationItemSelectedListener true
                    }
                    R.id.item_lib->{
                        fragmentTransaction.replace(R.id.home_fragment, ProjectListFragment()).commit()
                        return@OnNavigationItemSelectedListener true
//                        viewpager.setCurrentItem(1)
                    }
                    R.id.item_find->{
//                        viewpager.setCurrentItem(2)

                    }

                    R.id.item_more->{
//                        viewpager.setCurrentItem(4)

                    }
                }
                true
            }
        )



//        viewpager.addOnPageChangeListener(object:ViewPager.OnPageChangeListener{
//            override fun onPageScrolled(
//                position: Int,
//                positionOffset: Float,
//                positionOffsetPixels: Int
//            ) {
//                TODO("Not yet implemented")
//            }
//
//            override fun onPageSelected(position: Int) {
//                if (menuItem != null) {
//                    menuItem.setChecked(false);
//                } else {
//                    nav_view.getMenu().getItem(0).setChecked(false);
//                }
//                menuItem = nav_view.getMenu().getItem(position);
//                menuItem.setChecked(true);
//            }
//
//            override fun onPageScrollStateChanged(state: Int) {
//                TODO("Not yet implemented")
//            }
//        })
//
//
//        setupViewPager(viewpager)
//    }
//        val mFragments=ArrayList<Fragment>()
//
//        private fun setupViewPager(viewpager:ViewPager ){
//
//            mFragments.add(HomePageFragment.newInstance())
//            mFragments.add(SystemFragment.newInstance())
//
//            val adapter = object : FragmentStatePagerAdapter(
//                    supportFragmentManager,
//                    BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT
//                ) {
//                override fun getItem(position: Int): Fragment {
//                    return mFragments[position]
//                }
//
//                override fun getCount(): Int {
//                    return mFragments.size
//                }
//            }
//
//            viewpager.adapter = adapter
//
//
//        }

}
}