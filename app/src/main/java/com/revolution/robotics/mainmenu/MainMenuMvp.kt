package com.revolution.robotics.mainmenu

import com.revolution.robotics.Mvp

interface MainMenuMvp : Mvp {

    interface Model : Mvp.Model {
        val robotButton: MainMenuButtonViewModel
        val codingButton: MainMenuButtonViewModel
        val challengesButton: MainMenuButtonViewModel

        fun onCommunityIconClicked()
        fun onRoboticsIconClicked()
    }

    interface View : Mvp.View

    interface Presenter : Mvp.Presenter<View, Model> {
        fun navigateToRobots()
        fun navigateToCoding()
        fun navigateToChallenges()
    }
}
