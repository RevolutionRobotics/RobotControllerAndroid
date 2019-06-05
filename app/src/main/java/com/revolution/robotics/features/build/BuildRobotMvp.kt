package com.revolution.robotics.features.build

import com.revolution.robotics.core.Mvp
import com.revolution.robotics.core.domain.local.UserRobot
import com.revolution.robotics.core.domain.remote.BuildStep

interface BuildRobotMvp : Mvp {

    interface View : Mvp.View {
        fun onBuildStepsLoaded(steps: List<BuildStep>)
        fun onRobotSaveStarted()
    }

    interface Presenter : Mvp.Presenter<View, BuildRobotViewModel> {
        fun saveUserRobot(userRobot: UserRobot, createDefaultConfig: Boolean)
        fun loadBuildSteps(robotId: Int)
        fun letsDrive()
    }
}
