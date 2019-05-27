package com.revolution.robotics.features.challenges.challengeDetail

import com.revolution.robotics.views.toolbar.ToolbarOption
import com.revolution.robotics.views.toolbar.ToolbarViewModel

class ChallengeDetailToolbarViewModel : ToolbarViewModel() {
    override val isLogoVisible: Boolean = false
    override val hasBackOption: Boolean = true
    override val options: List<ToolbarOption> = emptyList()
}
