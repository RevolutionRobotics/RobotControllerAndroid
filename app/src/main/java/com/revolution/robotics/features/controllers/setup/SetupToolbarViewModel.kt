package com.revolution.robotics.features.controllers.setup

import com.revolution.robotics.R
import com.revolution.robotics.core.kodein.utils.ResourceResolver
import com.revolution.robotics.views.toolbar.RoboticsBluetoothToolbarViewModel
import com.revolution.robotics.views.toolbar.ToolbarOption

class SetupToolbarViewModel(resourceResolver: ResourceResolver) : RoboticsBluetoothToolbarViewModel() {

    override val isLogoVisible = false
    override val hasBackOption = true
    override val options = emptyList<ToolbarOption>()

    init {
        title.set(resourceResolver.string(R.string.controller_setup_screen_title))
    }
}
