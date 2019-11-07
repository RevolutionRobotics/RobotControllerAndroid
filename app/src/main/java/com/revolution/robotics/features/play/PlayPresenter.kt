package com.revolution.robotics.features.play

import android.net.Uri
import com.revolution.robotics.core.interactor.CreateConfigurationFileInteractor
import com.revolution.robotics.core.interactor.GetFullConfigurationInteractor
import com.revolution.robotics.core.interactor.GetUserRobotInteractor
import com.revolution.robotics.features.bluetooth.BluetoothManager
import com.revolution.robotics.features.shared.ErrorHandler
import org.revolutionrobotics.bluetooth.android.service.RoboticsLiveControllerService
import kotlin.math.max

class PlayPresenter(
    private val getUserRobotInteractor: GetUserRobotInteractor,
    private val getConfigurationInteractor: GetFullConfigurationInteractor,
    private val createConfigurationFileInteractor: CreateConfigurationFileInteractor,
    private val bluetoothManager: BluetoothManager,
    private val errorHandler: ErrorHandler
) : PlayMvp.Presenter {

    companion object {
        const val DIRECTION_VALUE_MAX = 255
    }

    override var view: PlayMvp.View? = null
    override var model: PlayViewModel? = null
    override var toolbarViewModel: PlayToolbarViewModel? = null
    override var reverseYAxis: Boolean = false
    override var reverseXAxis: Boolean = false

    private var liveControllerService: RoboticsLiveControllerService? = null

    override fun unregister(view: PlayMvp.View?) {
        liveControllerService?.stop()
        super.unregister(view)
    }

    override fun loadRobotName(robotId: Int) {
        getUserRobotInteractor.robotId = robotId
        getUserRobotInteractor.execute {
            toolbarViewModel?.title?.set(it?.name ?: "")
        }
    }

    override fun loadConfiguration(robotId: Int) {
        getConfigurationInteractor.robotId = robotId
        getConfigurationInteractor.execute { result ->
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
        if (reverseXAxis) {
            liveControllerService?.updateXDirection(max(0, DIRECTION_VALUE_MAX - value - 1))
        } else {
            liveControllerService?.updateXDirection(value)
        }
    }

    override fun onJoystickYAxisChanged(value: Int) {
        if (reverseYAxis) {
            liveControllerService?.updateYDirection(max(0, DIRECTION_VALUE_MAX - value - 1))
        } else {
            liveControllerService?.updateYDirection(value)
        }
    }

    override fun onButtonPressed(ordinal: Int) {
        liveControllerService?.onButtonPressed(ordinal - 1)
    }
}
