package com.revolution.robotics.features.myRobots

import com.revolution.robotics.core.Mvp

interface MyRobotsMvp : Mvp {

    interface View : Mvp.View

    interface Presenter : Mvp.Presenter<View, MyRobotsViewModel> {
        fun navigateToRobots()
    }
}
