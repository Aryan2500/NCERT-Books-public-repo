package com.virtual4vibe.ncertbooks.ui.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.google.android.material.tabs.TabLayout
import com.virtual4vibe.ncertbooks.R
import com.virtual4vibe.ncertbooks.common.createClassList
import com.virtual4vibe.ncertbooks.common.loadJSONFromAsset
import com.virtual4vibe.ncertbooks.databinding.FragmentHomeBinding
import com.virtual4vibe.ncertbooks.models.*
import com.virtual4vibe.ncertbooks.ui.adapter.BooksAdapter
import com.virtual4vibe.ncertbooks.ui.adapter.ClassPagerAdapter
import dagger.hilt.android.AndroidEntryPoint
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject


@AndroidEntryPoint
class HomeFragment : Fragment(R.layout.fragment_home) {
    lateinit var binding: FragmentHomeBinding
    val catList = ArrayList<Category>()
    lateinit var booksAdapter: BooksAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentHomeBinding.bind(view)
        setUpTabLayout()



//        deserializeJsonContent()
    }

    private fun deserializeJsonContent() {
        try {
            val obj = JSONObject(loadJSONFromAsset(requireContext()))
            val m_jArry: JSONArray = obj.getJSONArray("categories")

            for (i in 0 until m_jArry.length()) {
                val catItemList = ArrayList<CategoryItem>()
                catItemList.clear()

                val cat = m_jArry.getJSONObject(i)
                val items: JSONArray = cat.getJSONArray("items")

                for (i in 0 until items.length()) {
                    val classList = ArrayList<Class>()
                    classList.clear()

                    val item = items.getJSONObject(i)
                    val classes: JSONArray = item.getJSONArray("classes")

                    for (i in 0 until classes.length()) {
                        val subjList = ArrayList<Subject>()
                        subjList.clear()

                        val clas = classes.getJSONObject(i)
                        val subjects: JSONArray = clas.getJSONArray("subjects")

                        for (i in 0 until subjects.length()) {
                            val chapterList = ArrayList<Chapter>()
                            chapterList.clear()

                            val subject = subjects.getJSONObject(i)
                            val chapters: JSONArray = subject.getJSONArray("chapters")

                            for (i in 0 until chapters.length()) {
                                val chapter = chapters.getJSONObject(i)
                                chapterList.add(
                                    Chapter(
                                        chapter.getString("number"),
                                        chapter.getString("nameInEng"),
                                        chapter.getString("nameInHindi"),
                                        chapter.getString("engMLink"),
                                        chapter.getString("hindiMLink")
                                    )
                                )
                            }
//                            subjList.add(
//                                Subject(
//                                    subject.getString("number"),
//                                    subject.getString("nameInEng"),
//                                    subject.getString("nameInHindi"),
//                                    chapterList,
//                                )
//                            )
                        }

//                        classList.add(Class(clas.getString("class"), subjList))
                    }

                    catItemList.add(
                        CategoryItem(
                            item.getString("icon"),
                            item.getString("item_title"),
                            classList
                        )
                    )

                }

                catList.add(
                    Category(
                        cat.getString("id"),
                        cat.getString("cat_title"),
                        catItemList
                    )
                )
            }
            booksAdapter.differ.submitList(catList)


        } catch (e: JSONException) {
            e.printStackTrace()
        }
    }

    private fun setUpTabLayout( ) {
        binding.mediumTabLayout.addTab(binding.mediumTabLayout.newTab().setText("English Medium"))
        binding.mediumTabLayout.addTab(binding.mediumTabLayout.newTab().setText("हिंदी माधयम"))
        binding.mediumTabLayout.tabGravity = TabLayout.GRAVITY_FILL

        val pagerAdapter = ClassPagerAdapter(
            binding.mediumTabLayout.tabCount,
            childFragmentManager
        )
        binding.mediumViewPager.adapter = pagerAdapter

        binding.mediumViewPager.addOnPageChangeListener(TabLayout.TabLayoutOnPageChangeListener(binding.mediumTabLayout))
        binding.mediumTabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                binding.mediumViewPager.currentItem = tab!!.position
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) = Unit

            override fun onTabReselected(tab: TabLayout.Tab?) = Unit

        })
    }
}

