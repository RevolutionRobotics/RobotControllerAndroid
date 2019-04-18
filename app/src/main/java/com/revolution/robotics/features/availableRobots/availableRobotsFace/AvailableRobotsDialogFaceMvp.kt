package com.revolution.robotics.features.availableRobots.availableRobotsFace

import com.revolution.robotics.core.Mvp

interface AvailableRobotsDialogFaceMvp : Mvp {
    interface View : Mvp.View
    interface Presenter : Mvp.Presenter<View, AvailableRobotsDialogFaceViewModel> {
        fun onItemClick(idField: Any)
    }
}
