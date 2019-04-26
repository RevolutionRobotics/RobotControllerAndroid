package com.revolution.robotics.features.build.connect.availableRobotsFace

import com.revolution.robotics.core.Mvp

interface ConnectMvp : Mvp {

    interface View : Mvp.View

    interface Presenter : Mvp.Presenter<View, ConnectViewModel> {
        fun onItemClicked(robotId: Int)
    }
}
