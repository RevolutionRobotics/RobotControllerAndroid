package com.revolution.robotics.views.configure

import android.content.Context
import android.content.res.ColorStateList
import android.util.AttributeSet
import androidx.annotation.ColorRes
import androidx.appcompat.widget.AppCompatImageView
import com.revolution.robotics.R
import com.revolution.robotics.core.extensions.color

class PartConnectionIconView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) :
    AppCompatImageView(context, attrs, defStyleAttr) {
    private var isActive: Boolean = false
    @ColorRes
    private var activeIconColorRes: Int = R.color.grey_6d
    private var activeIconColor: Int? = null

    companion object {
        const val INACTIVE_ICON_RES = R.drawable.ic_config_add_grey
        const val ACTIVE_ICON_RES = R.drawable.ic_config_added
    }

    init {
        setLayerType(LAYER_TYPE_SOFTWARE, null)
        activeIconColor = context.color(activeIconColorRes)
        updateIcon()
    }

    fun setActiveColor(@ColorRes color: Int) {
        activeIconColorRes = color
        activeIconColor = context.color(activeIconColorRes)
        updateIcon()
    }

    fun setIsPartActive(isActive: Boolean) {
        if (this.isActive != isActive) {
            this.isActive = isActive
            updateIcon()
        }
    }

    private fun updateIcon() {
        if (isActive) {
            setImageResource(ACTIVE_ICON_RES)
            activeIconColor?.let { imageTintList = ColorStateList.valueOf(it) }
        } else {
            setImageResource(INACTIVE_ICON_RES)
            imageTintList = null
        }
        invalidate()
    }
}
