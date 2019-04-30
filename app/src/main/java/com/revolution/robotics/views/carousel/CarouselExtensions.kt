package com.revolution.robotics.views.carousel

import androidx.viewpager.widget.ViewPager
import kotlin.math.floor

const val CAROUSEL_OFFSCREEN_PAGE_LIMIT = 3
const val CAROUSEL_PADDING_MULTIPLIER = 0.3125

fun <T : CarouselAdapter<*>> ViewPager.initCarouselVariables(
    onPageChangeListener: ViewPager.OnPageChangeListener,
    adapter: T
) {
    this.adapter = adapter
    offscreenPageLimit = CAROUSEL_OFFSCREEN_PAGE_LIMIT
    setPageTransformer(false, CarouselPageTransformer(this))
    addOnPageChangeListener(onPageChangeListener)
}

fun ViewPager.initCarouselPadding(
    viewWidth: Int
) {
    val viewPagePadding = floor(viewWidth * CAROUSEL_PADDING_MULTIPLIER).toInt()
    setPaddingRelative(viewPagePadding, 0, viewPagePadding, 0)
}
