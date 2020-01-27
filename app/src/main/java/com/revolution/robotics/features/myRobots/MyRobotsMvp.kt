package com.revolution.robotics.features.myRobots

import com.revolution.robotics.BaseDialog
import com.revolution.robotics.core.Mvp
import com.revolution.robotics.core.domain.local.UserRobot

interface MyRobotsMvp : Mvp {
    interface View : Mvp.View {
        fun onRobotsChanged()
        fun showNextRobot()
        fun showPreviousRobot()
        fun showDialog(baseDialog: BaseDialog)
    }

    @Suppress("ComplexInterface", "TooManyFunctions")
    interface Presenter : Mvp.Presenter<View, MyRobotsViewModel> {
        fun clearEmptyNavigationFlag()
        fun onPageSelected(position: Int)
        fun nextButtonClick()
        fun previousButtonClick()
        fun navigateToWhoToBuild()
        fun onPlaySelected(robotId: Int)
        fun onContinueBuildingSelected(userRobot: UserRobot)
        fun onEditSelected(userRobot: UserRobot)
        fun duplicateRobot(userRobot: UserRobot)
        fun onMoreInfoClicked(userRobot: UserRobot)
        fun deleteRobot(userRobot: UserRobot, selectedPosition: Int)
        fun onDisabledItemClicked(userRobot: UserRobot)
    }
}
