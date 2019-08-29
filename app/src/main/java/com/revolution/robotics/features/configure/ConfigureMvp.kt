package com.revolution.robotics.features.configure

import com.revolution.robotics.BaseDialog
import com.revolution.robotics.core.Mvp
import com.revolution.robotics.core.domain.local.UserConfiguration
import com.revolution.robotics.core.domain.local.UserRobot

interface ConfigureMvp : Mvp {
    interface View : Mvp.View {
        fun showDialog(baseDialog: BaseDialog)
        fun setRobotId(robotId: Int)
        fun showConnectionsScreen(configId: Int)
        fun showControllerScreen(configId: Int)
        fun openMotorConfig(configId: Int, motorPort: MotorPort)
        fun openSensorConfig(configId: Int, sensorPort: SensorPort)
        fun updateConfig(userConfiguration: UserConfiguration)
        fun showSaveDialog(name: String, description: String)
        fun hideDrawer()
        fun showAdvancedSettingsPopup()
    }

    interface Presenter : Mvp.Presenter<View, ConfigureViewModel> {
        fun loadRobot(toolbarViewModel: ConfigureToolbarViewModel)
        fun onControllerTypeClicked()
        fun onDeleteClicked()
        fun deleteRobot()
        fun onAdvancedSettingsClicked()
        fun onDuplicateClicked()
        fun onRobotImageClicked()
        fun onBackgroundProgramsClicked()
        fun onPriorityClicked()
        fun editRobotDetails()
        fun onConnectionsTabSelected()
        fun onControllerTabSelected()
    }
}
