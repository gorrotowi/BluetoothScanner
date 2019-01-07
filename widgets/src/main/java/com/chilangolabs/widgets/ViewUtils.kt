package com.chilangolabs.widgets

import android.content.Context
import android.content.res.Resources
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AlphaAnimation
import android.view.animation.Animation
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.StringRes

val Int.dp: Int
    get() = (this / Resources.getSystem().displayMetrics.density).toInt()
val Int.px: Int
    get() = (this * Resources.getSystem().displayMetrics.density).toInt()

fun View.visible() {
    visibility = View.VISIBLE
}

fun View.invisible() {
    visibility = View.INVISIBLE
}

fun View.gone() {
    visibility = View.GONE
}

fun Context.showErrorConnection(@StringRes error: Int) {
    Toast.makeText(this, error, Toast.LENGTH_SHORT).show()
}

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

fun ViewGroup.inflate(layoutId: Int, attachToRoot: Boolean = false): View =
    LayoutInflater.from(context).inflate(layoutId, this, attachToRoot)