package com.revolution.robotics.features.configure

import com.revolution.robotics.core.Mvp
import com.revolution.robotics.views.dialogs.RoboticsDialog

interface ConfigureMvp : Mvp {
    interface View : Mvp.View {
        fun showDialog(roboticsDialog: RoboticsDialog)
        fun startConnectionFlow()
        fun showConnectionsScreen()
        fun showControllerScreen()
    }

    interface Presenter : Mvp.Presenter<View, ConfigureViewModel> {
        fun onRobotImageClicked()
        fun saveConfiguration()
        fun onBluetoothClicked()
        fun onConnectionsTabSelected()
        fun onControllerTabSelected()
    }
}
