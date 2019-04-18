package com.revolution.robotics.myRobots

import com.revolution.robotics.Mvp

interface MyRobotsMvp : Mvp {

    interface View : Mvp.View

    interface Presenter : Mvp.Presenter<View, MyRobotsViewModel> {
        fun navigateToRobots()
    }

}