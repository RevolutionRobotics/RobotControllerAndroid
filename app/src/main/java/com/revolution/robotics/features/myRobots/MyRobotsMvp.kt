package com.revolution.robotics.features.myRobots

import com.revolution.robotics.core.Mvp

interface MyRobotsMvp : Mvp {
    interface View : Mvp.View {
        fun onRobotsLoaded()
        fun showNextRobot()
        fun showPreviousRobot()
    }

    interface Presenter : Mvp.Presenter<View, MyRobotsViewModel> {
        fun onPageSelected(position: Int)
        fun nextButtonClick()
        fun previousButtonClick()
        fun navigateToWhoToBuild()
        fun onPlaySelected(robotId: Int)
        fun onEditSelected(robotId: Int)
        fun onDeleteSelected(robotId: Int)
    }
}
