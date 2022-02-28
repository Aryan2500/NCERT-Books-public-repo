package com.virtual4vibe.ncertbooks.ui

import android.os.Bundle
import android.widget.PopupMenu
import androidx.navigation.fragment.NavHostFragment
import com.unity3d.ads.UnityAds
import com.virtual4vibe.ncertbooks.R
import com.virtual4vibe.ncertbooks.common.TEST_MODE
import com.virtual4vibe.ncertbooks.common.UNITY_GAME_ID
import com.virtual4vibe.ncertbooks.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : BaseActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupSmoothBottomMenu()


    }

    private fun setupSmoothBottomMenu() {
        val popupMenu = PopupMenu(this@MainActivity, null)
        popupMenu.inflate(R.menu.bottom_menu)
        val menu = popupMenu.menu
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.fragmentContainerView) as NavHostFragment?
        val navController = navHostFragment!!.navController
        binding.bottomBar.setupWithNavController(menu, navController)
    }
}