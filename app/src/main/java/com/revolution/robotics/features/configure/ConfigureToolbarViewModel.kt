package com.revolution.robotics.features.configure

import com.revolution.robotics.R
import com.revolution.robotics.views.toolbar.RoboticsBluetoothToolbarViewModel
import com.revolution.robotics.views.toolbar.ToolbarOption

class ConfigureToolbarViewModel(presenter: ConfigureMvp.Presenter) : RoboticsBluetoothToolbarViewModel() {

    companion object {
        val OVERFLOW_ID: Int = -123
    }

    override val isLogoVisible = false
    override val hasBackOption = true
    override val options = listOf(
        ToolbarOption(R.drawable.ic_delete, presenter::onDeleteClicked),
        ToolbarOption(R.drawable.ic_copy, presenter::onDuplicateClicked),
        ToolbarOption(R.drawable.ic_camera, presenter::onRobotImageClicked),
        ToolbarOption(R.drawable.ic_edit, presenter::editRobotDetails),
        ToolbarOption(OVERFLOW_ID, R.drawable.ic_settings, presenter::onAdvancedSettingsClicked)
    )
}
