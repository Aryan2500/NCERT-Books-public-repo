package com.virtual4vibe.ncertbooks.ui.adapter

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.virtual4vibe.ncertbooks.models.Class
import com.virtual4vibe.ncertbooks.ui.fragments.ClassFragment

class ClassPagerAdapter(  var totalTabs: Int, fm: FragmentManager) : FragmentPagerAdapter(fm) {


    override fun getCount(): Int {
        return totalTabs
    }

    override fun getItem(position: Int): Fragment {
        return when (position) {
            0 -> {
                //0 for eng medium
                val bundle = Bundle()
                bundle.putInt("medium" , 0)
                ClassFragment.newInstance(bundle)
            }
           else-> {
               //1 for hindi medium
               val bundle = Bundle()
               bundle.putInt("medium" , 1)
               ClassFragment.newInstance(bundle)
            }

        }
    }
}