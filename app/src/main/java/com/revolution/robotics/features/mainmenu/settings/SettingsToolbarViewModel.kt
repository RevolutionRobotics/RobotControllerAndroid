package com.revolution.robotics.features.mainmenu.settings

import com.revolution.robotics.R
import com.revolution.robotics.core.kodein.utils.ResourceResolver
import com.revolution.robotics.views.toolbar.ToolbarOption
import com.revolution.robotics.views.toolbar.RoboticsBluetoothToolbarViewModel

class SettingsToolbarViewModel(resourceResolver: ResourceResolver) : RoboticsBluetoothToolbarViewModel() {
    override val isLogoVisible = false
    override val hasBackOption = true
    override val options = emptyList<ToolbarOption>()

    init {
        title.set(resourceResolver.string(R.string.settings_screen_title))
    }
}
