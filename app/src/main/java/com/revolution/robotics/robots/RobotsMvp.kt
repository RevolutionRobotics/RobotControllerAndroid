package com.revolution.robotics.robots

import com.revolution.robotics.Mvp

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
