package com.revolution.robotics.features.mainmenu.settings.firmware

import com.revolution.robotics.R
import com.revolution.robotics.core.kodein.utils.ResourceResolver
import com.revolution.robotics.views.toolbar.ToolbarOption
import com.revolution.robotics.views.toolbar.RoboticsBluetoothToolbarViewModel

class FirmwareToolbarViewModel(resourceResolver: ResourceResolver) : RoboticsBluetoothToolbarViewModel() {
    override val isLogoVisible: Boolean = false
    override val hasBackOption: Boolean = true
    override val options: List<ToolbarOption> = emptyList()

    init {
        title.set(resourceResolver.string(R.string.firmware_screen_title))
    }
}
