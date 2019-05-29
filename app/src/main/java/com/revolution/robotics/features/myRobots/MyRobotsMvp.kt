package com.revolution.robotics.features.myRobots

import com.revolution.robotics.core.Mvp
import com.revolution.robotics.core.domain.local.UserRobot

interface MyRobotsMvp : Mvp {
    interface View : Mvp.View {
        fun onRobotsChanged()
        fun showNextRobot()
        fun showPreviousRobot()
        fun deleteRobot(userRobot: UserRobot)
    }

    interface Presenter : Mvp.Presenter<View, MyRobotsViewModel> {
        fun onPageSelected(position: Int)
        fun nextButtonClick()
        fun previousButtonClick()
        fun navigateToWhoToBuild()
        fun onPlaySelected(configId: Int)
        fun onContinueBuildingSelected(robot: UserRobot)
        fun onEditSelected(userRobot: UserRobot)
        fun onDeleteSelected(userRobot: UserRobot)
        fun deleteRobot(userRobot: UserRobot, selectedPosition: Int)
    }
}
