package com.revolution.robotics.views.carousel

import android.view.View
import androidx.viewpager.widget.ViewPager

class CarouselPageTransformer(private val viewPager: ViewPager) : ViewPager.PageTransformer {

    companion object {
        private const val ITEM_SCALE_BASE = 0.75f
        private const val ITEM_SCALE_DYNAMIC = 0.25f
    }

    override fun transformPage(page: View, position: Float) {
        val pageWidth =
            viewPager.measuredWidth - viewPager.paddingLeft - viewPager.paddingRight
        val paddingLeft = viewPager.paddingLeft
        val transformPos = (page.left - (viewPager.scrollX + paddingLeft)).toFloat() / pageWidth
        val normalizedPosition = Math.abs(Math.abs(transformPos) - 1)

        if (transformPos in -1f..1f) {
            page.scaleX = ITEM_SCALE_BASE + ITEM_SCALE_DYNAMIC * normalizedPosition
            page.scaleY = ITEM_SCALE_BASE + ITEM_SCALE_DYNAMIC * normalizedPosition
        } else {
            page.scaleX = ITEM_SCALE_BASE
            page.scaleY = ITEM_SCALE_BASE
        }
    }
}
