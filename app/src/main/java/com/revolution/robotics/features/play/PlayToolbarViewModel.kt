package com.revolution.robotics.features.play

import com.revolution.robotics.R
import com.revolution.robotics.core.kodein.utils.ResourceResolver
import com.revolution.robotics.views.toolbar.ToolbarOption
import com.revolution.robotics.views.toolbar.ToolbarViewModel

class PlayToolbarViewModel(resourceResolver: ResourceResolver) : ToolbarViewModel() {

    override val isLogoVisible = false
    override val hasBackOption = true
    override val options = emptyList<ToolbarOption>()

    init {
        title.set(resourceResolver.string(R.string.play_screen_title))
    }
}