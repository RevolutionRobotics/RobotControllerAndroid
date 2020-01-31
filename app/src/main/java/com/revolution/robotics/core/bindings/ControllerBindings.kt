package com.revolution.robotics.core.bindings

import android.view.View
import androidx.databinding.BindingAdapter
import com.revolution.robotics.R
import com.revolution.robotics.core.domain.local.UserProgram
import com.revolution.robotics.features.play.ButtonPressListener
import com.revolution.robotics.views.ConnectionLineView
import com.revolution.robotics.views.controllerSetup.ProgramConnectionButton


@BindingAdapter("program", "isProgramSelected")
fun setProgramDetails(button: ProgramConnectionButton, program: UserProgram?, isProgramSelected: Boolean?) {
    if (program == null) {
        button.setIsActive(false)
    } else {
        button.setIsActive(true)
        button.setActiveName(program.name)
    }
    button.setIsSelected(isProgramSelected == true)
}

@BindingAdapter("isDriveTrain")
fun setDrivetrainButton(button: ProgramConnectionButton, isDrivetrain: Boolean) {
    if (isDrivetrain) {
        button.setDrivetrain()
    }
}

@BindingAdapter("program", "isProgramSelected")
fun setProgramDetails(line: ConnectionLineView, program: UserProgram?, isProgramSelected: Boolean?) {
    val (isDashed, color) = when {
        isProgramSelected == true -> false to com.revolution.robotics.R.color.white
        program != null -> false to R.color.robotics_red
        else -> true to R.color.grey_6d
    }
    line.setIsDashed(isDashed)
    line.setLineColor(color)
}

@BindingAdapter("program", "isProgramSelected")
fun setProgramDetails(view: View, program: UserProgram?, isProgramSelected: Boolean?) {
    view.setBackgroundResource(
        if (isProgramSelected == true) {
            R.drawable.controller_setup_button_selected
        } else {
            if (program == null) {
                R.drawable.controller_setup_button_unassigned
            } else {
                R.drawable.controller_setup_button_assigned
            }
        }
    )
}

@BindingAdapter("buttonPressListener")
fun setOnTouchListener(view: View, buttonPressListener: ButtonPressListener?) {
    if (buttonPressListener != null)
    {
        view.setOnTouchListener(buttonPressListener)
    }
}
