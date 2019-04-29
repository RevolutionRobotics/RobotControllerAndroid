package com.revolution.robotics.features.mainmenu

import com.revolution.robotics.R
import com.revolution.robotics.views.toolbar.ToolbarOption
import com.revolution.robotics.views.toolbar.ToolbarViewModel

class MainMenuToolbarViewModel(presenter: MainMenuMvp.Presenter) : ToolbarViewModel {

    override val isLogoVisible = true
    override val hasBackOption = false
    override val title: String? = null
    override val options = listOf(
        ToolbarOption(R.drawable.ic_community, presenter::onCommunityClicked),
        ToolbarOption(R.drawable.ic_gears, presenter::onSettingsClicked)
    )
}
