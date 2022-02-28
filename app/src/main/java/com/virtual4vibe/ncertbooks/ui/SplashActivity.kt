package com.virtual4vibe.ncertbooks.ui

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.Window
import android.view.WindowManager
import com.unity3d.ads.UnityAds
import com.virtual4vibe.ncertbooks.R
import com.virtual4vibe.ncertbooks.common.TEST_MODE
import com.virtual4vibe.ncertbooks.common.UNITY_GAME_ID
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SplashActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_splash)


        CoroutineScope(Dispatchers.IO).launch {
            try {
                //unity ads initialization
                UnityAds.initialize(this@SplashActivity, UNITY_GAME_ID, null, TEST_MODE)
            }catch (e:Exception){}

        }

        Handler().postDelayed({
            Intent(this, MainActivity::class.java).apply {
                startActivity(this)
                finish()
            }
        }, 450)


    }
}