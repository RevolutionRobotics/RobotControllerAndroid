package com.revolution.robotics.core.extensions

import android.content.res.TypedArray
import android.os.Build
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup
import android.view.ViewTreeObserver
import android.widget.TextView
import androidx.annotation.StyleRes
import androidx.annotation.StyleableRes
import androidx.viewpager.widget.ViewPager

@JvmOverloads
fun View.useStyledAttributes(
    set: AttributeSet?,
    @StyleableRes attrs: IntArray,
    defStyleAttr: Int = 0,
    defStyleRes: Int = 0,
    block: TypedArray.() -> Unit
) = set?.let {
    val typedArray = context.theme.obtainStyledAttributes(set, attrs, defStyleAttr, defStyleRes)
    try {
        block(typedArray)
    } finally {
        typedArray.recycle()
    }
}

fun ViewPager.onPageSelected(listener: (position: Int) -> Unit) {
    this.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
        override fun onPageScrollStateChanged(state: Int) {
        }

        override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
        }

        override fun onPageSelected(position: Int) = listener(position)
    })
}

var View.visible: Boolean
    set(value) {
        this.visibility = if (value) View.VISIBLE else View.GONE
    }
    get() = this.visibility == View.VISIBLE

val ViewGroup.views: List<View>
    get() = (0 until childCount).map { getChildAt(it) }

fun TextView.setAppearance(@StyleRes textAppearanceResId: Int) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
        setTextAppearance(textAppearanceResId)
    } else {
        @Suppress("DEPRECATION")
        setTextAppearance(context, textAppearanceResId)
    }
}

inline fun View.waitForLayout(crossinline f: () -> Unit) = with(viewTreeObserver) {
    addOnGlobalLayoutListener(object : ViewTreeObserver.OnGlobalLayoutListener {
        override fun onGlobalLayout() {
            viewTreeObserver.removeOnGlobalLayoutListener(this)
            f()
        }
    })
}

inline fun View.waitForPreDraw(crossinline f: () -> Boolean) = with(viewTreeObserver) {
    addOnPreDrawListener(object : ViewTreeObserver.OnPreDrawListener {
        override fun onPreDraw(): Boolean {
            viewTreeObserver.removeOnPreDrawListener(this)
            return f()
        }
    })
}
