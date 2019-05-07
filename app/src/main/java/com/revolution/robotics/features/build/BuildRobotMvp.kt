package com.revolution.robotics.features.build

import com.revolution.robotics.core.Mvp
import com.revolution.robotics.core.domain.local.UserRobot
import com.revolution.robotics.core.domain.remote.BuildStep
import com.revolution.robotics.core.domain.remote.Robot
import com.revolution.robotics.core.domain.shared.RobotDescriptor

interface BuildRobotMvp : Mvp {

    interface View : Mvp.View {
        fun onUserRobotLoaded(userRobot: UserRobot?)
        fun onBuildStepsLoaded(steps: List<BuildStep>)
    }

    interface Presenter : Mvp.Presenter<View, BuildRobotViewModel> {
        fun loadUserRobot(robotId: Int)
        fun createNewRobot(robot: RobotDescriptor?, currentBuildStep: BuildStep?)
        fun saveUserRobot(userRobot: UserRobot)
        fun loadBuildSteps(robotId: Int)
    }
}
