package com.revolution.robotics.features.robots

import com.revolution.robotics.core.Mvp

interface RobotsMvp : Mvp {
    interface View : Mvp.View {
        fun showNextRobot()
        fun showPreviousRobot()
    }

    interface Presenter : Mvp.Presenter<View, RobotsViewModel> {
        fun onPageSelected(position: Int)
        fun nextButtonClick()
        fun previousButtonClick()
    }
}
