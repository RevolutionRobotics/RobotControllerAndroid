package com.revolution.robotics.features.whoToBuild

import com.revolution.robotics.core.Mvp
import com.revolution.robotics.core.domain.remote.Robot

interface WhoToBuildMvp : Mvp {
    interface View : Mvp.View {
        fun onRobotsLoaded()
        fun showNextRobot()
        fun showPreviousRobot()
    }

    interface Presenter : Mvp.Presenter<View, WhoToBuildViewModel> {
        fun onPageSelected(position: Int)
        fun nextButtonClick()
        fun previousButtonClick()
        fun onRobotSelected(robot: Robot)
    }
}
