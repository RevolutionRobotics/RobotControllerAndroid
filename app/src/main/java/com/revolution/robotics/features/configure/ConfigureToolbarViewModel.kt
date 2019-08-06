package com.revolution.robotics.features.configure

import com.revolution.robotics.R
import com.revolution.robotics.views.toolbar.RoboticsBluetoothToolbarViewModel
import com.revolution.robotics.views.toolbar.ToolbarOption

class ConfigureToolbarViewModel(presenter: ConfigureMvp.Presenter) : RoboticsBluetoothToolbarViewModel() {

    override val isLogoVisible = false
    override val hasBackOption = true
    override val options = listOf(
        ToolbarOption(R.drawable.ic_controller, presenter::onControllerTypeClicked),
        ToolbarOption(R.drawable.ic_delete, presenter::onDeleteClicked),
        ToolbarOption(R.drawable.ic_copy, presenter::onDuplicateClicked),
        ToolbarOption(R.drawable.ic_camera, presenter::onRobotImageClicked),
        ToolbarOption(R.drawable.ic_edit, presenter::saveConfiguration)
    )
}
