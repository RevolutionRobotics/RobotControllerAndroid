package com.revolution.robotics.features.build

import com.revolution.robotics.core.domain.local.UserRobot
import com.revolution.robotics.core.domain.remote.Configuration
import com.revolution.robotics.core.domain.remote.Controller
import com.revolution.robotics.core.domain.remote.Program
import com.revolution.robotics.core.interactor.AssignConfigIntoARobotInteractor
import com.revolution.robotics.core.interactor.SaveUserRobotInteractor
import com.revolution.robotics.core.interactor.firebase.BuildStepInteractor
import com.revolution.robotics.core.interactor.firebase.ConfigurationInteractor
import com.revolution.robotics.core.interactor.firebase.ControllerInteractor
import com.revolution.robotics.core.interactor.firebase.ProgramsInteractor
import com.revolution.robotics.core.utils.Navigator
import com.revolution.robotics.features.controllers.ControllerType

class BuildRobotPresenter(
    private val buildStepInteractor: BuildStepInteractor,
    private val assignConfigIntoARobotInteractor: AssignConfigIntoARobotInteractor,
    private val saveUserRobotInteractor: SaveUserRobotInteractor,
    private val configurationInteractor: ConfigurationInteractor,
    private val controllerInteractor: ControllerInteractor,
    private val programsInteractor: ProgramsInteractor,
    private val navigator: Navigator
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

    override fun letsDrive() {
        controller?.let { controller ->
            userRobot?.let { robot ->
                when (ControllerType.fromId(controller.type)) {
                    ControllerType.GAMER ->
                        navigator.navigate(BuildRobotFragmentDirections.toPlayGamer(robot.configurationId))
                    ControllerType.MULTITASKER ->
                        navigator.navigate(BuildRobotFragmentDirections.toPlayMultitasker(robot.configurationId))
                    ControllerType.DRIVER ->
                        navigator.navigate(BuildRobotFragmentDirections.toPlayDriver(robot.configurationId))
                }
            }
        }
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
            saveUserRobotInteractor.execute { savedRobot ->
                this.userRobot = savedRobot
                view?.onRobotSaved()
            }
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
        userRobot?.let { userRobot ->
            saveUserRobotInteractor.userRobot = userRobot
            saveUserRobotInteractor.execute({ savedRobot ->
                this.userRobot = savedRobot
                assignConfig(configuration, controller, programs)
            }, {
                // TODO Error handling
            })
        }
    }

    private fun assignConfig(configuration: Configuration?, controller: Controller?, programs: List<Program>) {
        assignConfigIntoARobotInteractor.controller = controller
        assignConfigIntoARobotInteractor.configuration = configuration
        assignConfigIntoARobotInteractor.programs = programs
        userRobot?.let { userRobot ->
            assignConfigIntoARobotInteractor.userRobot = userRobot
            assignConfigIntoARobotInteractor.execute({ savedRobot ->
                this.userRobot = savedRobot
                view?.onRobotSaved()
            }, {
                // TODO Error handling
            })
        }
    }
}
