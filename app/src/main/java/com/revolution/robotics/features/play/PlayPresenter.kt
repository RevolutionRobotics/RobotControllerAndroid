package com.revolution.robotics.features.play

import android.net.Uri
import com.revolution.bluetooth.service.RoboticsLiveControllerService
import com.revolution.robotics.core.interactor.CreateConfigurationFileInteractor
import com.revolution.robotics.core.interactor.GetControllerNameInteractor
import com.revolution.robotics.core.interactor.GetFullConfigurationInteractor
import com.revolution.robotics.features.bluetooth.BluetoothManager
import com.revolution.robotics.features.shared.ErrorHandler

class PlayPresenter(
    private val getConfigurationInteractor: GetFullConfigurationInteractor,
    private val createConfigurationFileInteractor: CreateConfigurationFileInteractor,
    private val getControllerNameInteractor: GetControllerNameInteractor,
    private val bluetoothManager: BluetoothManager,
    private val errorHandler: ErrorHandler
) : PlayMvp.Presenter {

    companion object {
        const val DIRECTION_VALUE_MAX = 255
    }

    override var view: PlayMvp.View? = null
    override var model: PlayViewModel? = null
    override var toolbarViewModel: PlayToolbarViewModel? = null

    private var liveControllerService: RoboticsLiveControllerService? = null

    override fun loadControllerName(configId: Int) {
        getControllerNameInteractor.configurationId = configId
        getControllerNameInteractor.execute {
            toolbarViewModel?.title?.set(it)
        }
    }

    override fun unregister(view: PlayMvp.View?) {
        liveControllerService?.stop()
        super.unregister(view)
    }

    override fun loadConfiguration(configId: Int) {
        getConfigurationInteractor.userConfigId = configId
        getConfigurationInteractor.execute { result ->
            toolbarViewModel?.title?.set(result.controller?.userController?.name)
            view?.let {
                createConfigurationFile(result)
            }
        }
    }

    private fun createConfigurationFile(controllerData: FullControllerData) {
        createConfigurationFileInteractor.controllerData = controllerData
        createConfigurationFileInteractor.execute { configurationUri ->
            view?.let {
                sendConfiguration(configurationUri, controllerData)
            }
        }
    }

    private fun sendConfiguration(configurationFile: Uri, controllerData: FullControllerData) {
        bluetoothManager.getConfigurationService().sendConfiguration(configurationFile,
            onSuccess = {
                view?.apply {
                    onControllerLoaded(controllerData)
                    liveControllerService = bluetoothManager.bleConnectionHandler.liveControllerService
                    liveControllerService?.start()
                }
            },
            onError = {
                view?.onControllerLoadingError()
                errorHandler.onError()
            })
    }

    override fun onDeviceDisconnected() {
        liveControllerService?.stop()
    }

    override fun onJoystickXAxisChanged(value: Int) {
        liveControllerService?.updateXDirection(value)
    }

    override fun onJoystickYAxisChanged(value: Int) {
        liveControllerService?.updateYDirection(DIRECTION_VALUE_MAX - value - 1)
    }

    override fun onButtonPressed(ordinal: Int) {
        liveControllerService?.onButtonPressed(ordinal - 1)
    }
}
