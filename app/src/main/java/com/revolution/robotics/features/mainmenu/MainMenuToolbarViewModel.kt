package com.revolution.robotics.features.mainmenu

import com.revolution.robotics.views.toolbar.ToolbarOption
import com.revolution.robotics.views.toolbar.RoboticsBluetoothToolbarViewModel

class MainMenuToolbarViewModel : RoboticsBluetoothToolbarViewModel() {

    override val isLogoVisible = true
    override val hasBackOption = false
    override val options: List<ToolbarOption> = emptyList()
}
