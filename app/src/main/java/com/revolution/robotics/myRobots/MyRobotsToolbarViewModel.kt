package com.revolution.robotics.myRobots

import com.revolution.robotics.R
import com.revolution.robotics.core.kodein.utils.ResourceResolver
import com.revolution.robotics.core.views.toolbar.ToolbarOption
import com.revolution.robotics.core.views.toolbar.ToolbarViewModel

class MyRobotsToolbarViewModel(resourceResolver: ResourceResolver) : ToolbarViewModel {

    override val isLogoVisible = false
    override val hasBackOption = true
    override val title = resourceResolver.string(R.string.my_robots_title)
    override val options = emptyList<ToolbarOption>()
}
