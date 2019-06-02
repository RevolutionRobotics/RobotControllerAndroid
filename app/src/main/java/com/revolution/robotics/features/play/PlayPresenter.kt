package com.revolution.robotics.features.play

import com.revolution.bluetooth.communication.RoboticsDeviceConnector
import com.revolution.bluetooth.service.RoboticsLiveControllerService
import com.revolution.robotics.core.interactor.GetUserConfigurationInteractor
import com.revolution.robotics.core.interactor.GetUserControllerInteractor

class PlayPresenter(
    private val getUserConfigurationInteractor: GetUserConfigurationInteractor,
    private val getUserControllerInteractor: GetUserControllerInteractor
) : PlayMvp.Presenter {

    override var view: PlayMvp.View? = null
    override var model: PlayViewModel? = null

    private var liveControllerService: RoboticsLiveControllerService? = null

    override fun unregister(view: PlayMvp.View?) {
        liveControllerService?.stop()
        super.unregister(view)
    }

    override fun loadConfiguration(configId: Int) {
        getUserConfigurationInteractor.userConfigId = configId
        getUserConfigurationInteractor.execute { userConfig ->
            userConfig?.controller?.let { id ->
                getUserControllerInteractor.id = id
                getUserControllerInteractor.execute { result ->
                    view?.onControllerLoaded(result)
                }
            }
        }
    }

    override fun onDeviceConnected(handler: RoboticsDeviceConnector) {
        liveControllerService = handler.liveControllerService
        liveControllerService?.start()
    }

    override fun onDeviceDisconnected() {
        liveControllerService?.stop()
    }

    override fun onJoystickXAxisChanged(value: Int) {
        liveControllerService?.updateXDirection(value)
    }

    override fun onJoystickYAxisChanged(value: Int) {
        liveControllerService?.updateYDirection(value)
    }

    override fun onButtonPressed(ordinal: Int) {
        liveControllerService?.negateButtonState(ordinal - 1)
    }
}
