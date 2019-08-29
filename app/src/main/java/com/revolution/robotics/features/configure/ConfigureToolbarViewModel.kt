package com.revolution.robotics.features.configure

import com.revolution.robotics.views.toolbar.RoboticsBluetoothToolbarViewModel
import com.revolution.robotics.views.toolbar.ToolbarOption

class ConfigureToolbarViewModel(presenter: ConfigureMvp.Presenter) : RoboticsBluetoothToolbarViewModel() {


    override val isLogoVisible = true
    override val hasBackOption = false
    override val options = emptyList<ToolbarOption>()
}
