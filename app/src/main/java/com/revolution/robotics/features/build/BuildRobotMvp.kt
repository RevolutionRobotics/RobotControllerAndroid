package com.revolution.robotics.features.build

import com.revolution.robotics.core.Mvp
import com.revolution.robotics.core.domain.remote.BuildStep

interface BuildRobotMvp : Mvp {

    interface View : Mvp.View {
        fun onBuildStepsLoaded(steps: List<BuildStep>)
    }

    interface Presenter : Mvp.Presenter<View, BuildRobotViewModel> {
        fun loadBuildSteps(robotId: Int)
    }
}