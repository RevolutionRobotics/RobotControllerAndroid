package com.revolution.robotics.core.extensions

import androidx.core.view.postDelayed
import androidx.viewpager.widget.ViewPager

fun ViewPager.forceApplyTransformer() =
    postDelayed(50) {
        beginFakeDrag()
        fakeDragBy(1.0f)
        endFakeDrag()
    }
