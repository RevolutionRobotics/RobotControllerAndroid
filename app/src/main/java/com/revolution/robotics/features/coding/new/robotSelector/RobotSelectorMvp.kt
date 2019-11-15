package com.revolution.robotics.features.coding.new.robotSelector

import com.revolution.robotics.core.Mvp
import com.revolution.robotics.core.domain.local.UserRobot
import com.revolution.robotics.features.coding.programs.adapter.RobotViewModel

interface RobotSelectorMvp : Mvp {

    interface View : Mvp.View {
        fun onRobotsLoaded(robots: List<RobotViewModel>)
        fun onRobotSelected(robot: UserRobot)
    }

    interface Presenter : Mvp.Presenter<View, RobotSelectorViewModel> {
        fun loadRobots()
        fun onRobotSelected(robot: UserRobot)
    }
}
