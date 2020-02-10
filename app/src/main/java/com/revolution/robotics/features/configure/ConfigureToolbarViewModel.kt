package com.revolution.robotics.features.configure

import com.revolution.robotics.R
import com.revolution.robotics.views.toolbar.RoboticsBluetoothToolbarViewModel
import com.revolution.robotics.views.toolbar.ToolbarOption

class ConfigureToolbarViewModel(presenter: ConfigureMvp.Presenter) : RoboticsBluetoothToolbarViewModel() {

    companion object {
        const val OVERFLOW_ID: Int = 123
    }

    override val isLogoVisible = false
    override val hasBackOption = true
    override val options = listOf(
        ToolbarOption(OVERFLOW_ID, R.drawable.ic_settings, presenter::onAdvancedSettingsClicked)
    )
}
