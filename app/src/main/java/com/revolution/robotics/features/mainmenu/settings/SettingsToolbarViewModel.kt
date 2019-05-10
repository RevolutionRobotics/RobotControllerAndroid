package com.revolution.robotics.features.mainmenu.settings

import com.revolution.robotics.R
import com.revolution.robotics.core.kodein.utils.ResourceResolver
import com.revolution.robotics.views.toolbar.ToolbarOption
import com.revolution.robotics.views.toolbar.ToolbarViewModel

class SettingsToolbarViewModel(resourceResolver: ResourceResolver) : ToolbarViewModel {
    override val isLogoVisible = false
    override val hasBackOption = true
    override val title: String? = resourceResolver.string(R.string.settings_title)
    override val options = emptyList<ToolbarOption>()
}
