package com.revolution.robotics.features.challenges.challengeList

import com.revolution.robotics.views.toolbar.ToolbarOption
import com.revolution.robotics.views.toolbar.RoboticsBluetoothToolbarViewModel

class ChallengeListToolbarViewModel : RoboticsBluetoothToolbarViewModel() {
    override val isLogoVisible: Boolean = false
    override val hasBackOption: Boolean = true
    override val options: List<ToolbarOption> = emptyList()
}
