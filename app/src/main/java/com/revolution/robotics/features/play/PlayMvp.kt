package com.revolution.robotics.features.play

import com.revolution.bluetooth.communication.RoboticsDeviceConnector
import com.revolution.robotics.core.Mvp

interface PlayMvp : Mvp {

    interface View : Mvp.View {
        fun onControllerLoaded(data: FullControllerData)
    }

    interface Presenter : Mvp.Presenter<View, PlayViewModel> {
        fun loadConfiguration(configId: Int)
        fun onDeviceConnected(handler: RoboticsDeviceConnector)
        fun onDeviceDisconnected()
        fun onJoystickXAxisChanged(value: Int)
        fun onJoystickYAxisChanged(value: Int)
        fun onButtonPressed(ordinal: Int)
    }
}
