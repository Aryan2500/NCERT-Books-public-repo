package com.virtual4vibe.ncertbooks.common

import android.view.View
import android.view.animation.TranslateAnimation

fun slideDown(view: View) {
    view.visibility = View.VISIBLE
    var animate = TranslateAnimation(
        0F,
        0F,
        -view.height.toFloat(),
        0F,
    )
    animate.duration = 900
    animate.fillAfter = true
    view.startAnimation(animate)
}

fun slideUp(view: View) {

    var animate = TranslateAnimation(
        0F,
        0F,
        0F,
        -(200+view.height.toFloat())
    )
    animate.duration = 850
    animate.fillAfter = true
    view.startAnimation(animate)
//    view.visibility = View.GONE
}