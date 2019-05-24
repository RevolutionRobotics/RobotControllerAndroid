package com.revolution.robotics.features.mainmenu

import com.revolution.robotics.core.Mvp
import com.revolution.robotics.views.dialogs.RoboticsDialog

interface MainMenuMvp : Mvp {

    interface View : Mvp.View {
        fun showDialog(roboticsDialog: RoboticsDialog)
    }

    interface Presenter : Mvp.Presenter<View, MainMenuViewModel> {
        fun navigateToMyRobots()
        fun navigateToCoding()
        fun navigateToChallengeList()
        fun onCommunityClicked()
        fun onSettingsClicked()
    }
}
