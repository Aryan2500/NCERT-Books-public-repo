package com.virtual4vibe.ncertbooks.ui.adapter

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.virtual4vibe.ncertbooks.ui.fragments.ClassFragment
import com.virtual4vibe.ncertbooks.ui.fragments.SubjectFragment

class SubjectPagerAdapter(var totalTabs: Int, fm: FragmentManager) : FragmentPagerAdapter(fm) {
    lateinit var topTitle :String
    lateinit var mediumCode :String
    lateinit var className :String
    override fun getCount(): Int {
       return totalTabs
    }

    override fun getItem(position: Int): Fragment {
        return when (position) {
            0 -> {
                val bundle = Bundle()
                bundle.putString("medium" , mediumCode )
                bundle.putString("topTitle" , topTitle)
                bundle.putString("className" , className)
                bundle.putString("streamCode" , "science")
                SubjectFragment.newInstance(bundle)
            }
            1-> {
                val bundle = Bundle()
                bundle.putString("medium" , mediumCode )
                bundle.putString("topTitle" , topTitle)
                bundle.putString("className" , className)
                bundle.putString("streamCode" , "commerce")
                SubjectFragment.newInstance(bundle)

            }
            else -> {
                val bundle = Bundle()
                bundle.putString("medium" , mediumCode )
                bundle.putString("topTitle" , topTitle)
                bundle.putString("className" , className)
                bundle.putString("streamCode" , "arts")
                SubjectFragment.newInstance(bundle)

            }
        }
    }
}