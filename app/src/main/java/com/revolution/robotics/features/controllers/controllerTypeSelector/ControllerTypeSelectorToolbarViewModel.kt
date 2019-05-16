package com.revolution.robotics.features.controllers.controllerTypeSelector

import com.revolution.robotics.R
import com.revolution.robotics.core.kodein.utils.ResourceResolver
import com.revolution.robotics.views.toolbar.ToolbarOption
import com.revolution.robotics.views.toolbar.ToolbarViewModel

class ControllerTypeSelectorToolbarViewModel(resourceResolver: ResourceResolver) : ToolbarViewModel() {

    override val isLogoVisible = false
    override val hasBackOption = true
    override val options = emptyList<ToolbarOption>()

    init {
        title.set(resourceResolver.string(R.string.controller_type_selector_screen_title))
    }
}
