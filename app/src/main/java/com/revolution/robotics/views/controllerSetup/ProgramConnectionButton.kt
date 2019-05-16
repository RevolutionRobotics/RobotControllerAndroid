package com.revolution.robotics.views.controllerSetup

import android.content.Context
import android.graphics.Color
import android.util.AttributeSet
import android.view.View
import android.widget.FrameLayout
import android.widget.ImageView
import androidx.annotation.StringRes
import com.revolution.robotics.R
import com.revolution.robotics.core.extensions.color
import com.revolution.robotics.views.chippedBox.ChippedBoxConfig
import com.revolution.robotics.views.chippedBox.ChippedBoxDrawable
import kotlinx.android.synthetic.main.view_part_connection_button.view.icon
import kotlinx.android.synthetic.main.view_part_connection_button.view.name

class ProgramConnectionButton @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {

    companion object {
        private val CONFIG_INACTIVE = ChippedBoxConfig.Builder()
            .backgroundColorResource(R.color.grey_28)
            .borderColorResource(R.color.grey_6d)
            .borderSize(R.dimen.dimen_1dp)
            .chipSize(R.dimen.dimen_10dp)
            .clipLeftSide(true)
            .customDashed(R.dimen.configure_connections_line_dash_width, R.dimen.configure_connections_line_gap_width)
            .create()
        private val CONFIG_ACTIVE = ChippedBoxConfig.Builder()
            .backgroundColorResource(R.color.grey_28)
            .borderColorResource(R.color.robotics_red)
            .borderSize(R.dimen.dimen_1dp)
            .chipSize(R.dimen.dimen_10dp)
            .clipLeftSide(true)
            .create()
        private val CONFIG_SELECTED = ChippedBoxConfig.Builder()
            .backgroundColorResource(R.color.white)
            .borderColorResource(R.color.white)
            .borderSize(R.dimen.dimen_1dp)
            .chipSize(R.dimen.dimen_10dp)
            .clipLeftSide(true)
            .create()
    }

    @StringRes
    private var inactiveNameRes: Int = R.string.controller_setup_empty
    private var activeName: String? = null
    private var isProgramActive: Boolean = false
    private var isProgramSelected: Boolean = false
    private var inactiveChippedBoxDrawable = ChippedBoxDrawable(context, CONFIG_INACTIVE)
    private var activeChippedBoxDrawable = ChippedBoxDrawable(context, CONFIG_ACTIVE)
    private var selectedChippedBoxDrawable = ChippedBoxDrawable(context, CONFIG_SELECTED)

    init {
        View.inflate(context, R.layout.view_program_connection_button, this)
        setLayerType(ImageView.LAYER_TYPE_SOFTWARE, null)
        updateButton()
    }

    fun setActiveName(name: String?) {
        if (activeName != name) {
            activeName = name
            updateButton()
        }
    }

    fun setIsActive(isActive: Boolean) {
        if (this.isProgramActive != isActive) {
            this.isProgramActive = isActive
            updateButton()
        }
    }

    fun setIsSelected(isSelected: Boolean) {
        if (this.isProgramSelected != isSelected) {
            this.isProgramSelected = isSelected
            updateButton()
        }
    }

    private fun updateButton() {
        when {
            isProgramSelected ->
                background = selectedChippedBoxDrawable
            isProgramActive -> {
                background = activeChippedBoxDrawable
                name.text = activeName
            }
            else -> {
                background = inactiveChippedBoxDrawable
                name.setText(inactiveNameRes)
            }
        }
        name.setTextColor(context.color(if (isProgramSelected) R.color.black else R.color.white))
        invalidate()
    }
}
