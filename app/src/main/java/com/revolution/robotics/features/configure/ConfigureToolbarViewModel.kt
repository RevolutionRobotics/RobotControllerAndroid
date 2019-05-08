package com.revolution.robotics.features.configure

import com.revolution.robotics.R
import com.revolution.robotics.views.toolbar.ToolbarOption
import com.revolution.robotics.views.toolbar.ToolbarViewModel

class ConfigureToolbarViewModel(presenter: ConfigureMvp.Presenter) : ToolbarViewModel {

    override val isLogoVisible = false
    override val hasBackOption = true
    override val title = "Todo: Name Of Robot"
    override val options = listOf(
        ToolbarOption(R.drawable.ic_camera, presenter::onRobotImageClicked),
        ToolbarOption(R.drawable.ic_save, presenter::saveConfiguration)
    )
}
