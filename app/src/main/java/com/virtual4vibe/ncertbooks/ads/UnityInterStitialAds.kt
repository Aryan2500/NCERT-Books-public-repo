package com.virtual4vibe.ncertbooks.ads


import android.app.Activity
import android.content.Context
import android.util.Log
import com.unity3d.ads.IUnityAdsListener
import com.unity3d.ads.UnityAds

object UnityInterStitialAds  {

    var listener : IUnityAdsListener = object :  IUnityAdsListener {
        override fun onUnityAdsReady(p0: String?) = Unit

        override fun onUnityAdsStart(p0: String?) = Unit

        override fun onUnityAdsFinish(p0: String?, p1: UnityAds.FinishState?) = Unit

        override fun onUnityAdsError(p0: UnityAds.UnityAdsError?, p1: String?)  = Unit

    }
    fun loadAd(adUnitId: String ){
        UnityAds.load(adUnitId)
    }

    fun showAd (adUnitId : String , a:Activity ){
        UnityAds.setListener(listener)
        if (UnityAds.isReady (adUnitId)) {
            UnityAds.show (a, adUnitId)
        }
    }




}

