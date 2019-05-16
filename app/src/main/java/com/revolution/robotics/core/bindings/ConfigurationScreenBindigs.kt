package com.revolution.robotics.core.bindings

import android.widget.Button
import androidx.annotation.ColorRes
import androidx.databinding.BindingAdapter
import com.revolution.robotics.core.extensions.color
import com.revolution.robotics.features.configure.connections.RobotPartModel
import com.revolution.robotics.views.configure.PartConnectionButton
import com.revolution.robotics.views.configure.PartConnectionIconView
import com.revolution.robotics.views.ConnectionLineView

@BindingAdapter("bindRobotPartModel")
fun setPartConnectionLineViewRobotPart(lineView: ConnectionLineView, robotPartModel: RobotPartModel?) {
    lineView.setIsDashed(robotPartModel?.isActive != true)
    if (robotPartModel != null) lineView.setLineColor(robotPartModel.color)
}

@BindingAdapter("bindRobotPartModel")
fun setPartConnectionIconViewRobotPart(
    partConnectionIconView: PartConnectionIconView,
    robotPartModel: RobotPartModel?
) {
    partConnectionIconView.setIsPartActive(robotPartModel?.isActive == true)
    if (robotPartModel != null) partConnectionIconView.setActiveColor(robotPartModel.color)
}

@BindingAdapter("bindRobotPartModel")
fun setPartConnectionButtonRobotPart(partConnectionButton: PartConnectionButton, robotPartModel: RobotPartModel?) {
    partConnectionButton.setIsPartActive(robotPartModel?.isActive == true)
    partConnectionButton.setPortSelected(robotPartModel?.isSelected == true)
    robotPartModel?.let { model ->
        partConnectionButton.setColor(model.color)
        partConnectionButton.setActiveName(model.name)
        partConnectionButton.setActiveIcon(model.icon)
    }
}

@BindingAdapter("bindTextColor")
fun setButtonTextColor(button: Button, @ColorRes colorRes: Int) {
    if (colorRes != 0) button.setTextColor(button.context.color(colorRes))
}
