package com.revolution.robotics.features.configure

import com.revolution.robotics.core.Mvp
import com.revolution.robotics.core.domain.local.UserConfiguration
import com.revolution.robotics.core.domain.local.UserRobot
import com.revolution.robotics.views.dialogs.RoboticsDialog

interface ConfigureMvp : Mvp {
    interface View : Mvp.View {
        fun showDialog(roboticsDialog: RoboticsDialog)
        fun showConnectionsScreen()
        fun showControllerScreen()
        fun openMotorConfig(motorPort: MotorPort)
        fun openSensorConfig(sensorPort: SensorPort)
        fun updateConfig(userConfiguration: UserConfiguration)
        fun showSaveDialog(name: String, description: String)
        fun hideDrawer()
    }

    interface Presenter : Mvp.Presenter<View, ConfigureViewModel> {
        fun initUI(userRobot: UserRobot, toolbarViewModel: ConfigureToolbarViewModel)
        fun onRobotImageClicked()
        fun saveConfiguration()
        fun onConnectionsTabSelected()
        fun onControllerTabSelected()
        fun clearStorage()
    }
}
