package com.revolution.robotics.features.mainmenu

import com.revolution.robotics.core.Mvp

interface MainMenuMvp : Mvp {

    interface View : Mvp.View

    interface Presenter : Mvp.Presenter<View, MainMenuViewModel> {
        fun navigateToMyRobots()
        fun navigateToCoding()
        fun navigateToChallenges()
        fun onCommunityClicked()
        fun onSettingsClicked()
    }
}
