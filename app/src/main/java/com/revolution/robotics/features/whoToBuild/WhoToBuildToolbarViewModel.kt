package com.revolution.robotics.features.whoToBuild

import com.revolution.robotics.R
import com.revolution.robotics.core.kodein.utils.ResourceResolver
import com.revolution.robotics.views.toolbar.ToolbarOption
import com.revolution.robotics.views.toolbar.ToolbarViewModel

class WhoToBuildToolbarViewModel(resourceResolver: ResourceResolver) : ToolbarViewModel {

    override val isLogoVisible = false
    override val hasBackOption = true
    override val title = resourceResolver.string(R.string.who_to_build_title)
    override val options = emptyList<ToolbarOption>()
}
