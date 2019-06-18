package com.revolution.robotics.features.play

import androidx.core.net.toUri
import com.revolution.bluetooth.service.RoboticsLiveControllerService
import com.revolution.robotics.core.interactor.GetFullConfigurationInteractor
import com.revolution.robotics.core.kodein.utils.ApplicationContextProvider
import com.revolution.robotics.features.bluetooth.BluetoothManager
import com.revolution.robotics.features.play.configurationBuilder.ConfigurationBuilder
import com.revolution.robotics.features.shared.ErrorHandler
import java.io.File

class PlayPresenter(
    private val interactor: GetFullConfigurationInteractor,
    private val bluetoothManager: BluetoothManager,
    private val errorHandler: ErrorHandler,
    private val contextProvider: ApplicationContextProvider
) : PlayMvp.Presenter {

    companion object {
        const val DIRECTION_VALUE_MAX = 255

        private const val CONFIGURATION_FILE_NAME = "config.json"
    }

    override var view: PlayMvp.View? = null
    override var model: PlayViewModel? = null

    private var liveControllerService: RoboticsLiveControllerService? = null
    private val configurationBuilder = ConfigurationBuilder()

    override fun unregister(view: PlayMvp.View?) {
        liveControllerService?.stop()
        super.unregister(view)
    }

    override fun loadConfiguration(configId: Int) {
        interactor.userConfigId = configId
        interactor.execute { result ->
            val configurationUri =
                File("${contextProvider.applicationContext.filesDir}/$CONFIGURATION_FILE_NAME").apply {
                    if (!exists()) {
                        createNewFile()
                    }
                    writeText(configurationBuilder.createConfigurationJson(result))
                }.toUri()
            bluetoothManager.getConfigurationService().sendConfiguration(configurationUri,
                onSuccess = {
                    liveControllerService = bluetoothManager.bleConnectionHandler.liveControllerService
                    liveControllerService?.start()
                    view?.onControllerLoaded(result)
                },
                onError = {
                    view?.onControllerLoadingError()
                    errorHandler.onError()
                })
        }
    }

    override fun onDeviceDisconnected() {
        liveControllerService?.stop()
    }

    override fun onJoystickXAxisChanged(value: Int) {
        liveControllerService?.updateXDirection(value)
    }

    override fun onJoystickYAxisChanged(value: Int) {
        liveControllerService?.updateYDirection(DIRECTION_VALUE_MAX - value)
    }

    override fun onButtonPressed(ordinal: Int) {
        liveControllerService?.negateButtonState(ordinal - 1)
    }
}
