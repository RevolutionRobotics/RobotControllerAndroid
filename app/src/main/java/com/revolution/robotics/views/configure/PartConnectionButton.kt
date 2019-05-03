package com.revolution.robotics.views.configure

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.FrameLayout
import android.widget.ImageView
import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.revolution.robotics.R
import com.revolution.robotics.views.chippedBox.ChippedBoxConfig
import com.revolution.robotics.views.chippedBox.ChippedBoxDrawable
import kotlinx.android.synthetic.main.view_part_connection_button.view.icon
import kotlinx.android.synthetic.main.view_part_connection_button.view.name

class PartConnectionButton @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) :
    FrameLayout(context, attrs, defStyleAttr) {
    private var isActive: Boolean = false
    @ColorRes
    private var activeColorRes: Int = R.color.grey_6d

    private var inactiveChippedBoxDrawable: ChippedBoxDrawable? = null
    private var activeChippedBoxDrawable: ChippedBoxDrawable? = null

    @DrawableRes
    private var inactiveIconRes: Int = R.drawable.ic_config_add
    @StringRes
    private var inactiveNameRes: Int = R.string.configure_connections_missing_part_name

    @DrawableRes
    private var activeIconRes: Int = inactiveIconRes
    @StringRes
    private var activeNameRes: Int = inactiveNameRes

    companion object {
        val INACTIVE_CHIPPED_BOX_CONFIG = ChippedBoxConfig.Builder()
            .backgroundColorResource(R.color.grey_28)
            .borderColorResource(R.color.grey_6d)
            .borderSize(R.dimen.dimen_1dp)
            .chipSize(R.dimen.dimen_10dp)
            .chipTopRight(true)
            .chipBottomLeft(true)
            .clipLeftSide(true)
            .customDashed(
                R.dimen.configure_connections_line_dash_width,
                R.dimen.configure_connections_line_gap_width
            )
            .create()
    }

    init {
        initView()
        initAttributes(attrs)
        initVariables()
        updateButton()
    }

    private fun initView() {
        View.inflate(context, R.layout.view_part_connection_button, this)
    }

    private fun initAttributes(attrs: AttributeSet?) {
        if (attrs == null) return
        val a = context.theme.obtainStyledAttributes(
            attrs,
            R.styleable.PartConnectionButton,
            0, 0
        )

        try {
            inactiveIconRes = a.getResourceId(R.styleable.PartConnectionButton_inactivePartIcon, inactiveIconRes)
            inactiveNameRes = a.getResourceId(R.styleable.PartConnectionButton_inactivePartName, inactiveNameRes)
        } finally {
            a.recycle()
        }
    }

    private fun initVariables() {
        setLayerType(ImageView.LAYER_TYPE_SOFTWARE, null)
        inactiveChippedBoxDrawable = ChippedBoxDrawable(context, INACTIVE_CHIPPED_BOX_CONFIG)
        initActiveChippedBoxConfig(activeColorRes)
        updateButton()
    }

    private fun initActiveChippedBoxConfig(@ColorRes color: Int) {
        activeChippedBoxDrawable = ChippedBoxDrawable(
            context, ChippedBoxConfig.Builder()
                .backgroundColorResource(R.color.grey_28)
                .borderColorResource(color)
                .borderSize(R.dimen.dimen_1dp)
                .chipSize(R.dimen.dimen_10dp)
                .chipTopRight(true)
                .chipBottomLeft(true)
                .clipLeftSide(true)
                .isDashed(false)
                .create()
        )
    }

    fun setActiveColor(@ColorRes color: Int) {
        activeColorRes = color
        initActiveChippedBoxConfig(activeColorRes)
        updateButton()
    }

    fun setActiveIcon(@DrawableRes icon: Int) {
        activeIconRes = icon
        updateButton()
    }

    fun setActiveName(@StringRes name: Int) {
        activeNameRes = name
        updateButton()
    }

    fun setIsPartActive(isActive: Boolean) {
        if (this.isActive != isActive) {
            this.isActive = isActive
            updateButton()
        }
    }

    fun setOnClick(onClick: () -> Unit) {
        setOnClickListener { onClick() }
    }

    private fun updateButton() {
        if (isActive) {
            background = activeChippedBoxDrawable
            icon.setImageResource(activeIconRes)
            name.setText(activeNameRes)
        } else {
            background = inactiveChippedBoxDrawable
            icon.setImageResource(inactiveIconRes)
            name.setText(inactiveNameRes)
        }

        invalidate()
    }
}
