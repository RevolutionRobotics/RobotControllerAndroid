package com.revolution.robotics.features.build

import com.revolution.robotics.core.domain.local.ProgramLocalFiles
import com.revolution.robotics.core.domain.local.UserRobot
import com.revolution.robotics.core.domain.remote.Configuration
import com.revolution.robotics.core.domain.remote.Controller
import com.revolution.robotics.core.domain.remote.Program
import com.revolution.robotics.core.eventBus.dialog.DialogEvent
import com.revolution.robotics.core.eventBus.dialog.DialogEventBus
import com.revolution.robotics.core.interactor.AssignConfigToRobotInteractor
import com.revolution.robotics.core.interactor.SaveUserRobotInteractor
import com.revolution.robotics.core.interactor.firebase.BuildStepInteractor
import com.revolution.robotics.core.interactor.firebase.ConfigurationInteractor
import com.revolution.robotics.core.interactor.firebase.ControllerInteractor
import com.revolution.robotics.core.interactor.firebase.FirebaseProgramDownloader
import com.revolution.robotics.core.interactor.firebase.ProgramsInteractor
import com.revolution.robotics.core.utils.Navigator
import com.revolution.robotics.features.controllers.ControllerType
import com.revolution.robotics.features.shared.ErrorHandler

class BuildRobotPresenter(
    private val buildStepInteractor: BuildStepInteractor,
    private val assignConfigToRobotInteractor: AssignConfigToRobotInteractor,
    private val saveUserRobotInteractor: SaveUserRobotInteractor,
    private val configurationInteractor: ConfigurationInteractor,
    private val controllerInteractor: ControllerInteractor,
    private val programsInteractor: ProgramsInteractor,
    private val navigator: Navigator,
    private val dialogEventBus: DialogEventBus,
    private val firebaseProgramDownloader: FirebaseProgramDownloader,
    private val errorHandler: ErrorHandler
) : BuildRobotMvp.Presenter {

    override var view: BuildRobotMvp.View? = null
    override var model: BuildRobotViewModel? = null

    private var configuration: Configuration? = null
    private var controllers: List<Controller>? = null
    private var userRobot: UserRobot? = null

    override fun loadBuildSteps(robotId: Int) {
        buildStepInteractor.robotId = robotId
        buildStepInteractor.execute { steps ->
            view?.onBuildStepsLoaded(steps)
        }
    }

    override fun letsDrive() {
        configuration?.let { configuration ->
            userRobot?.let { robot ->
                startPlayFragment(configuration, robot, controllers)
            }
        }
    }

    private fun startPlayFragment(configuration: Configuration, robot: UserRobot, controllers: List<Controller>?) {
        controllers?.find { it.id == configuration.controller }?.let { controller ->
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

    override fun saveUserRobot(userRobot: UserRobot, createDefaultConfig: Boolean) {
        if (createDefaultConfig) {
            view?.onRobotSaveStarted()
            this.userRobot = userRobot
            configurationInteractor.configId = userRobot.configurationId
            configurationInteractor.execute { config ->
                configuration = config
                downloadControllerInfos(config.id)
            }
        } else {
            saveUserRobotInteractor.userRobot = userRobot
            saveUserRobotInteractor.execute { savedRobot ->
                this.userRobot = savedRobot
            }
        }
    }

    private fun downloadControllerInfos(configurationId: Int) {
        controllerInteractor.configurationId = configurationId.toString()
        controllerInteractor.execute(onResponse = { controllers ->
            this.controllers = controllers
            val programIds = mutableListOf<String>()
            controllers.forEach { controller ->
                controller.getProgramIds().forEach { programId ->
                    if (!programIds.contains(programId)) {
                        programIds.add(programId)
                    }
                }
            }
            downloadPrograms(programIds)
        }, onError = {
            errorHandler.onError()
            dialogEventBus.publish(DialogEvent.ROBOT_CREATE_ERROR)
        })
    }

    private fun downloadPrograms(ids: List<String>) {
        programsInteractor.programIds = ids
        programsInteractor.execute(onResponse = { programs ->
            downloadProgramFiles(programs)
        }, onError = {
            errorHandler.onError()
            dialogEventBus.publish(DialogEvent.ROBOT_CREATE_ERROR)
        })
    }

    private fun downloadProgramFiles(programs: List<Program>) {
        firebaseProgramDownloader.downloadProgramFiles(programs, onResponse = {
            createLocalObjects(configuration, controllers, programs, it)
        }, onError = {
            errorHandler.onError()
            dialogEventBus.publish(DialogEvent.ROBOT_CREATE_ERROR)
        })
    }

    private fun createLocalObjects(
        configuration: Configuration?,
        controllers: List<Controller>?,
        programs: List<Program>,
        programFiles: List<ProgramLocalFiles>
    ) {
        userRobot?.let { userRobot ->
            saveUserRobotInteractor.userRobot = userRobot
            saveUserRobotInteractor.execute(onResponse = { savedRobot ->
                this.userRobot = savedRobot
                assignConfig(configuration, controllers, programs, programFiles)
            }, onError = {
                errorHandler.onError()
                dialogEventBus.publish(DialogEvent.ROBOT_CREATE_ERROR)
            })
        }
    }

    private fun assignConfig(
        configuration: Configuration?,
        controllers: List<Controller>?,
        programs: List<Program>,
        programFiles: List<ProgramLocalFiles>
    ) {
        assignConfigToRobotInteractor.controllers = controllers
        assignConfigToRobotInteractor.configuration = configuration
        assignConfigToRobotInteractor.programs = programs
        assignConfigToRobotInteractor.programLocalFiles = programFiles
        userRobot?.let { userRobot ->
            assignConfigToRobotInteractor.userRobot = userRobot
            assignConfigToRobotInteractor.execute(onResponse = { savedRobot ->
                this.userRobot = savedRobot
                dialogEventBus.publish(DialogEvent.ROBOT_CREATED)
            }, onError = {
                errorHandler.onError()
                dialogEventBus.publish(DialogEvent.ROBOT_CREATE_ERROR)
            })
        }
    }
}
