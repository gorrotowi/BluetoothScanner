package com.chilangolabs.widgets

import android.content.res.Resources
import android.view.animation.AlphaAnimation
import android.view.animation.Animation
import android.widget.TextView

val Int.dp: Int
    get() = (this / Resources.getSystem().displayMetrics.density).toInt()
val Int.px: Int
    get() = (this * Resources.getSystem().displayMetrics.density).toInt()

fun TextView.setTextAnimate(newText: String) {
    val anim = AlphaAnimation(1f, 0f)
    anim.duration = 100
    anim.repeatCount = 1
    anim.repeatMode = Animation.REVERSE

    anim.setAnimationListener(object : Animation.AnimationListener {
        override fun onAnimationRepeat(animation: Animation?) {
            this@setTextAnimate.text = newText
        }

        override fun onAnimationEnd(animation: Animation?) {}

        override fun onAnimationStart(animation: Animation?) {}
    })
    this.startAnimation(anim)
}