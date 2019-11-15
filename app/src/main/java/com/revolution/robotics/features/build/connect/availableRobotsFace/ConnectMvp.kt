package com.revolution.robotics.features.build.connect.availableRobotsFace

import com.revolution.robotics.core.Mvp
import com.revolution.robotics.features.build.connect.adapter.ConnectRobotItem

interface ConnectMvp : Mvp {

    interface View : Mvp.View {
        fun onConnectionSuccess()
        fun onConnectionError()
    }

    interface Presenter : Mvp.Presenter<View, ConnectViewModel> {
        fun onItemClicked(robot: ConnectRobotItem)
    }
}
