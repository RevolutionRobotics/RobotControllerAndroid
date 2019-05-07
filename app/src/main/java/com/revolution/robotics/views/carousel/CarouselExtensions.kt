package com.revolution.robotics.views.carousel

import androidx.core.view.postDelayed
import androidx.viewpager.widget.ViewPager
import kotlin.math.floor

const val CAROUSEL_OFFSCREEN_PAGE_LIMIT = 3
const val CAROUSEL_PADDING_MULTIPLIER = 0.3125
const val PAGE_TRANSFORM_INIT_DELAY = 150L

fun <T : CarouselAdapter<*>> ViewPager.initCarouselVariables(
    onPageChangeListener: ViewPager.OnPageChangeListener,
    adapter: T
) {
    this.adapter = adapter
    offscreenPageLimit = CAROUSEL_OFFSCREEN_PAGE_LIMIT
    addOnPageChangeListener(onPageChangeListener)
}

fun ViewPager.initTransformerWithDelay() {
    postDelayed(PAGE_TRANSFORM_INIT_DELAY) {
        setPageTransformer(false, CarouselPageTransformer(this))
    }
}

@Suppress("OptionalUnit")
fun ViewPager.reInitTransformerWithDelay() {
    setPageTransformer(false, { _, _ -> Unit })
    initTransformerWithDelay()
}

fun ViewPager.initCarouselPadding(
    viewWidth: Int
) {
    val viewPagePadding = floor(viewWidth * CAROUSEL_PADDING_MULTIPLIER).toInt()
    setPaddingRelative(viewPagePadding, 0, viewPagePadding, 0)
}
