package com.revolution.robotics.features.build

import com.revolution.robotics.views.toolbar.ToolbarOption
import com.revolution.robotics.views.toolbar.ToolbarViewModel

class BuildRobotToolbarViewModel : ToolbarViewModel() {

    override val isLogoVisible = false
    override val hasBackOption = true
    override val options = emptyList<ToolbarOption>()
}
