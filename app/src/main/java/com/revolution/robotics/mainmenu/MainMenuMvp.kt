package com.revolution.robotics.mainmenu

import com.revolution.robotics.Mvp

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
