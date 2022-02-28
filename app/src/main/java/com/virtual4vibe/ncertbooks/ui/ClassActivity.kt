package com.virtual4vibe.ncertbooks.ui

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import com.google.android.material.tabs.TabLayout
import com.virtual4vibe.ncertbooks.databinding.ActivityClassBinding
import com.virtual4vibe.ncertbooks.models.Class
import com.virtual4vibe.ncertbooks.ui.adapter.ClassPagerAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ClassActivity : BaseActivity() {
    lateinit var bind: ActivityClassBinding

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bind = ActivityClassBinding.inflate(layoutInflater)
        setContentView(bind.root)

        val i = intent
        val catName = i.getStringExtra("categoryName")
        val catItemTitle = i.getStringExtra("itemTitle")
        bind.classActivityTitle.text = "$catName > $catItemTitle"

        val classList = i.getSerializableExtra("classList") as ArrayList<Class>

        Log.v("classList" , "$classList")

        setUpTabLayout(classList)
    }

    private fun setUpTabLayout(classList: ArrayList<Class>) {
        bind.mediumTabLayout.addTab(bind.mediumTabLayout.newTab().setText("English Medium"))
        bind.mediumTabLayout.addTab(bind.mediumTabLayout.newTab().setText("हिंदी माधयम"))
        bind.mediumTabLayout.tabGravity = TabLayout.GRAVITY_FILL

        val pagerAdapter = ClassPagerAdapter(bind.mediumTabLayout.tabCount, supportFragmentManager)
        bind.mediumViewPager.adapter = pagerAdapter

        bind.mediumViewPager.addOnPageChangeListener(TabLayout.TabLayoutOnPageChangeListener(bind.mediumTabLayout))
        bind.mediumTabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                bind.mediumViewPager.currentItem = tab!!.position
            }
            override fun onTabUnselected(tab: TabLayout.Tab?) =Unit

            override fun onTabReselected(tab: TabLayout.Tab?) =Unit

        })
    }

}