package com.revolution.robotics.views.controllerSetup

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.FrameLayout
import android.widget.ImageView
import androidx.annotation.StringRes
import com.revolution.robotics.R
import com.revolution.robotics.core.extensions.color
import com.revolution.robotics.core.extensions.string
import com.revolution.robotics.views.chippedBox.ChippedBoxConfig
import com.revolution.robotics.views.chippedBox.ChippedBoxDrawable
import kotlinx.android.synthetic.main.view_part_connection_button.view.name

class ProgramConnectionButton @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {

    companion object {
        private fun baseChippedBoxConfig() = ChippedBoxConfig.Builder()
            .borderSize(R.dimen.dimen_1dp)
            .chipSize(R.dimen.dimen_6dp)
            .clipLeftSide(true)

        private val CONFIG_INACTIVE = baseChippedBoxConfig()
            .backgroundColorResource(R.color.grey_28)
            .borderColorResource(R.color.grey_6d)
            .customDashed(R.dimen.configure_connections_line_dash_width, R.dimen.configure_connections_line_gap_width)
            .create()
        private val CONFIG_ACTIVE = baseChippedBoxConfig()
            .backgroundColorResource(R.color.grey_28)
            .borderColorResource(R.color.robotics_red)
            .create()
        private val CONFIG_SELECTED = baseChippedBoxConfig()
            .backgroundColorResource(R.color.white)
            .borderColorResource(R.color.white)
            .create()
        private val CONFIG_DRIVETRAIN = baseChippedBoxConfig()
            .backgroundColorResource(R.color.grey_28)
            .borderColorResource(R.color.grey_8e)
            .create()
    }

    @StringRes
    private var inactiveNameRes: Int = R.string.controller_setup_empty
    private var activeName: String? = null
    private var isProgramActive: Boolean = false
    private var isProgramSelected: Boolean = false
    private var isDrivetrain: Boolean = false
    private var inactiveChippedBoxDrawable = ChippedBoxDrawable(context, CONFIG_INACTIVE)
    private var activeChippedBoxDrawable = ChippedBoxDrawable(context, CONFIG_ACTIVE)
    private var selectedChippedBoxDrawable = ChippedBoxDrawable(context, CONFIG_SELECTED)
    private var drivetrainChippedBoxDrawable = ChippedBoxDrawable(context, CONFIG_DRIVETRAIN)

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

    fun setDrivetrain() {
        this.isDrivetrain = true
        this.isProgramSelected = false
        updateButton()
    }

    private fun updateButton() {
        when {
            isDrivetrain -> {
                background = drivetrainChippedBoxDrawable
                name.text = context.string(R.string.configure_motor_drivetrain_button_title).toLowerCase()
            }
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
