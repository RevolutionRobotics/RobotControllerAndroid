package com.revolution.robotics.features.play

import com.revolution.robotics.core.Mvp

interface PlayMvp : Mvp {

    interface View : Mvp.View {
        fun onControllerLoaded(data: FullControllerData)
        fun onControllerLoadingError()
    }

    interface Presenter : Mvp.Presenter<View, PlayViewModel> {

        var toolbarViewModel: PlayToolbarViewModel?
        var reverseYAxis: Boolean
        var reverseXAxis: Boolean

        fun loadRobotName(robotId: Int)
        fun loadConfiguration(robotId: Int)
        fun onDeviceDisconnected()
        fun onJoystickXAxisChanged(value: Int)
        fun onJoystickYAxisChanged(value: Int)
        fun onButtonPressed(ordinal: Int)
    }
}
