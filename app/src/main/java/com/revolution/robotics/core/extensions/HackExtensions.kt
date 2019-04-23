package com.revolution.robotics.core.extensions

import androidx.core.view.postDelayed
import androidx.viewpager.widget.ViewPager

fun ViewPager.forceApplyTransformer() {
    if (adapter?.count != 0) {
        beginFakeDrag()
        fakeDragBy(1.0f)
        endFakeDrag()
    } else {
        postDelayed(10, ::forceApplyTransformer)
    }
}
