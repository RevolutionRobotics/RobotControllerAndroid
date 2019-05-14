package com.revolution.robotics.features.mainmenu.settings.about

import com.revolution.robotics.R
import com.revolution.robotics.core.kodein.utils.ResourceResolver
import com.revolution.robotics.views.toolbar.ToolbarOption
import com.revolution.robotics.views.toolbar.ToolbarViewModel

class AboutToolbarViewModel(private val resourceResolver: ResourceResolver) : ToolbarViewModel() {
    override val isLogoVisible: Boolean = false
    override val hasBackOption: Boolean = true
    override val options: List<ToolbarOption> = emptyList()

    init {
        title.set(resourceResolver.string(R.string.about_screen_title))
    }
}