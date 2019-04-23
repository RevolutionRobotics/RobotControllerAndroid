package com.revolution.robotics.features.whoToBuild

import com.revolution.robotics.core.Mvp

interface WhoToBuildMvp : Mvp {
    interface View : Mvp.View {
        fun showNextRobot()
        fun showPreviousRobot()
    }

    interface Presenter : Mvp.Presenter<View, WhoToBuildViewModel> {
        fun onPageSelected(position: Int)
        fun nextButtonClick()
        fun previousButtonClick()
    }
}
