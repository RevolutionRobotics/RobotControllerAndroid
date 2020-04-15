package com.revolution.robotics.features.whoToBuild.download

import androidx.lifecycle.ViewModel
import com.revolution.robotics.core.Mvp

interface DownloadRobotMVP: Mvp {
    interface View : Mvp.View {
        fun showError()
        fun showSuccess()
    }

    interface Presenter : Mvp.Presenter<View, ViewModel> {
        fun downloadRobot(robotId: String)
    }
}