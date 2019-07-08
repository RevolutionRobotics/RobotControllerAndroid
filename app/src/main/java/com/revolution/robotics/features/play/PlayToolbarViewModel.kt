package com.revolution.robotics.features.play

import com.revolution.robotics.views.toolbar.ToolbarOption
import com.revolution.robotics.views.toolbar.RoboticsBluetoothToolbarViewModel

class PlayToolbarViewModel : RoboticsBluetoothToolbarViewModel() {

    override val isLogoVisible = false
    override val hasBackOption = true
    override val options = emptyList<ToolbarOption>()

    init {
        title.set("")
    }
}
