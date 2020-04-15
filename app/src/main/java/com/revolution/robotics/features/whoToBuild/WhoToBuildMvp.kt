package com.revolution.robotics.features.whoToBuild

import com.revolution.robotics.core.Mvp
import com.revolution.robotics.core.domain.remote.Robot
import com.revolution.robotics.features.whoToBuild.adapter.RobotsItem

interface WhoToBuildMvp : Mvp {
    interface View : Mvp.View {
        fun onRobotsLoaded()
        fun showNextRobot()
        fun showPreviousRobot()
        fun showDownloadDialog(robotId: String)
    }

    interface Presenter : Mvp.Presenter<View, WhoToBuildViewModel> {
        fun loadRobots()
        fun onPageSelected(position: Int)
        fun nextButtonClick()
        fun previousButtonClick()
        fun onRobotSelected(robot: Robot)
        fun onBuildYourOwnSelected()
        fun onDisabledItemClicked(robotsItem: RobotsItem)
    }
}
