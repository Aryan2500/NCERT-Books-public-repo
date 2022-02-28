package com.virtual4vibe.ncertbooks.ui

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.tabs.TabLayout
import com.virtual4vibe.ncertbooks.databinding.ActivitySubjectBinding
import com.virtual4vibe.ncertbooks.ui.adapter.SubjectPagerAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SubjectActvity : BaseActivity() {
    lateinit var bind: ActivitySubjectBinding
    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bind = ActivitySubjectBinding.inflate(layoutInflater)
        setContentView(bind.root)

        val i = intent
        val topTitleOfClassActivity = i.getStringExtra("topTitleOfClassActivity")
        val className = i.getStringExtra("className")
        val mediumCode = i.getStringExtra("mediumCode").toString()

        Log.v("streamcode" , "$mediumCode")
        bind.titlteSubjectAcitivity.text = "$topTitleOfClassActivity > $className"

        if(mediumCode=="0"){
            bind.tabLayout.addTab(bind.tabLayout.newTab().setText("Science"));
            bind.tabLayout.addTab(bind.tabLayout.newTab().setText("Commerce"));
            bind.tabLayout.addTab(bind.tabLayout.newTab().setText("Arts"));
        }else{
            bind.tabLayout.addTab(bind.tabLayout.newTab().setText("विज्ञान"));
            bind.tabLayout.addTab(bind.tabLayout.newTab().setText("कॉमर्स(व्यापार)"));
            bind.tabLayout.addTab(bind.tabLayout.newTab().setText("आर्ट्स"));
        }

        bind.tabLayout.tabGravity = TabLayout.GRAVITY_FILL

        val pagerAdapter = SubjectPagerAdapter(bind.tabLayout.tabCount, supportFragmentManager)
        pagerAdapter.topTitle =  "$topTitleOfClassActivity > $className"
        pagerAdapter.mediumCode = mediumCode
        pagerAdapter.className = className.toString()
        bind.viewPager.adapter = pagerAdapter

        bind.viewPager.addOnPageChangeListener(TabLayout.TabLayoutOnPageChangeListener(bind.tabLayout))
        bind.tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                bind.viewPager.currentItem = tab!!.position
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {

            }

            override fun onTabReselected(tab: TabLayout.Tab?) {

            }

        })

    }
}