package com.revolution.robotics.features.mainmenu

import com.revolution.robotics.BaseDialog
import com.revolution.robotics.core.Mvp

interface MainMenuMvp : Mvp {

    interface View : Mvp.View {
        fun showDialog(baseDialog: BaseDialog)
        fun showCongratulationsDialog()
    }

    interface Presenter : Mvp.Presenter<View, MainMenuViewModel> {
        fun handleOnboarding()
        fun navigateToMyRobots()
        fun navigateToCoding()
        fun navigateToChallengeList()
        fun onCommunityClicked()
        fun onSettingsClicked()
    }
}
