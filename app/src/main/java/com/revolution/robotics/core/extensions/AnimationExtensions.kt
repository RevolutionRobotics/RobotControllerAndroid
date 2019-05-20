package com.revolution.robotics.core.extensions

import android.view.animation.Animation

fun Animation.setListener(onStart: () -> Unit = {}, onEnd: () -> Unit = {}, onRepeat: () -> Unit = {}) {
    this.setAnimationListener(object : Animation.AnimationListener {
        override fun onAnimationRepeat(animation: Animation?) = onRepeat.invoke()
        override fun onAnimationEnd(animation: Animation?) = onEnd.invoke()
        override fun onAnimationStart(animation: Animation?) = onStart.invoke()
    })
}
