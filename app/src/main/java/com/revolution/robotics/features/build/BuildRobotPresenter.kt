package com.revolution.robotics.features.build

import com.revolution.robotics.core.domain.local.UserRobot
import com.revolution.robotics.core.domain.remote.Configuration
import com.revolution.robotics.core.domain.remote.Controller
import com.revolution.robotics.core.domain.remote.Program
import com.revolution.robotics.core.interactor.SaveUserRobotInteractor
import com.revolution.robotics.core.interactor.firebase.BuildStepInteractor
import com.revolution.robotics.core.interactor.firebase.ConfigurationInteractor
import com.revolution.robotics.core.interactor.firebase.ControllerInteractor
import com.revolution.robotics.core.interactor.firebase.ProgramsInteractor

class BuildRobotPresenter(
    private val buildStepInteractor: BuildStepInteractor,
    private val saveUserRobotInteractor: SaveUserRobotInteractor,
    private val configurationInteractor: ConfigurationInteractor,
    private val controllerInteractor: ControllerInteractor,
    private val programsInteractor: ProgramsInteractor
) : BuildRobotMvp.Presenter {

    override var view: BuildRobotMvp.View? = null
    override var model: BuildRobotViewModel? = null

    private var configuration: Configuration? = null
    private var controller: Controller? = null
    private var userRobot: UserRobot? = null

    override fun loadBuildSteps(robotId: Int) {
        buildStepInteractor.robotId = robotId
        buildStepInteractor.execute(
            onResponse = { steps ->
                view?.onBuildStepsLoaded(steps)
            },
            onError = { error ->
                // TODO add error handling
                error.printStackTrace()
            }
        )
    }

    override fun saveUserRobot(userRobot: UserRobot, createDefaultConfig: Boolean) {
        if (createDefaultConfig) {
            this.userRobot = userRobot
            configurationInteractor.configId = userRobot.configurationId
            configurationInteractor.execute(
                onResponse = { config ->
                    configuration = config
                    downloadControllerInfo(config.controller)
                },
                onError = {
                    // TODO Error handling
                })
        } else {
            saveUserRobotInteractor.userRobot = userRobot
            saveUserRobotInteractor.execute()
        }
    }

    private fun downloadControllerInfo(controllerId: String?) {
        controllerInteractor.controllerId = controllerId ?: ""
        controllerInteractor.execute({ controller ->
            this.controller = controller
            downloadPrograms(controller.getProgramIds())
        }, {
            // TODO Error handling
        })
    }

    private fun downloadPrograms(ids: List<String>) {
        programsInteractor.programIds = ids
        programsInteractor.execute({ programs ->
            createLocalObjects(configuration, controller, programs)
        }, {
            // TODO Error handling
        })
    }

    private fun createLocalObjects(configuration: Configuration?, controller: Controller?, programs: List<Program>) {
        saveUserRobotInteractor.controller = controller
        saveUserRobotInteractor.configuration = configuration
        saveUserRobotInteractor.programs = programs
        userRobot?.let {userRobot ->
            saveUserRobotInteractor.userRobot = userRobot
            saveUserRobotInteractor.execute()
        }
    }
}
