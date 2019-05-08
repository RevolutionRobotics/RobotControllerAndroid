package com.revolution.robotics.features.configure

import com.revolution.robotics.views.toolbar.ToolbarOption
import com.revolution.robotics.views.toolbar.ToolbarViewModel

class ConfigureToolbarViewModel : ToolbarViewModel {

    override val isLogoVisible = false
    override val hasBackOption = true
    override val title = "Todo: Name Of Robot"
    override val options = emptyList<ToolbarOption>()
}
