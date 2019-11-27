package com.revolution.robotics.features.coding.test

import android.net.Uri
import android.util.Base64
import androidx.lifecycle.ViewModel
import com.revolution.robotics.core.domain.local.UserController
import com.revolution.robotics.core.domain.local.UserControllerWithPrograms
import com.revolution.robotics.core.domain.local.UserProgram
import com.revolution.robotics.core.interactor.CreateConfigurationFileInteractor
import com.revolution.robotics.core.interactor.GetUserConfigForRobotInteractor
import com.revolution.robotics.features.bluetooth.BluetoothManager
import com.revolution.robotics.features.play.FullControllerData
import org.revolutionrobotics.bluetooth.android.service.RoboticsLiveControllerService

class TestCodePresenter(
    private val getUserConfigForRobotInteractor: GetUserConfigForRobotInteractor,
    private val createConfigurationFileInteractor: CreateConfigurationFileInteractor,
    private val bluetoothManager: BluetoothManager
) : TestCodeMvp.Presenter {

    override var view: TestCodeMvp.View? = null
    override var model: ViewModel? = null

    private var liveControllerService: RoboticsLiveControllerService? = null

    override fun runProgram(python: String, robotId: Int) {
        getUserConfigForRobotInteractor.robotId = robotId
        getUserConfigForRobotInteractor.execute { config ->
            val program = UserProgram(
                name = "test", robotId = 0, python = String(
                    Base64.encode(python.toByteArray(), Base64.NO_WRAP)
                )
            )
            val userControllerWithPrograms = UserControllerWithPrograms(
                userController = UserController(),
                programs = hashMapOf(),
                backgroundBindings = mutableListOf()
            )
            userControllerWithPrograms.addBackgroundProgram(program)
            val fullControllerData = FullControllerData(
                userConfiguration = config,
                controller = userControllerWithPrograms,
                sources = hashMapOf(program.name to program.python!!)
            )
            createConfigurationFile(fullControllerData)
        }
    }

    override fun unregister(view: TestCodeMvp.View?) {
        liveControllerService?.stop()
        super.unregister(view)
    }

    private fun createConfigurationFile(controllerData: FullControllerData) {
        createConfigurationFileInteractor.controllerData = controllerData
        createConfigurationFileInteractor.execute { configurationUri ->
            view?.let {
                sendConfiguration(configurationUri)
            }
        }
    }

    private fun sendConfiguration(configurationFile: Uri) {
        bluetoothManager.getConfigurationService().sendConfiguration(configurationFile,
            onSuccess = {
                view?.showConfigurationSent()
                liveControllerService = bluetoothManager.bleConnectionHandler.liveControllerService
                liveControllerService?.start()
            },
            onError = {
                view?.showError()
            })
    }
}