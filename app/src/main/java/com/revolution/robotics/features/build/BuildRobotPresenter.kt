package com.revolution.robotics.features.build

import com.revolution.robotics.core.domain.local.ProgramLocalFiles
import com.revolution.robotics.core.domain.local.UserRobot
import com.revolution.robotics.core.domain.remote.Configuration
import com.revolution.robotics.core.domain.remote.Controller
import com.revolution.robotics.core.domain.remote.Program
import com.revolution.robotics.core.interactor.AssignConfigIntoARobotInteractor
import com.revolution.robotics.core.interactor.SaveUserRobotInteractor
import com.revolution.robotics.core.interactor.firebase.BuildStepInteractor
import com.revolution.robotics.core.interactor.firebase.ConfigurationInteractor
import com.revolution.robotics.core.interactor.firebase.ControllerInteractor
import com.revolution.robotics.core.interactor.firebase.FirebaseProgramDownloader
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
    private val navigator: Navigator,
    private val firebaseProgramDownloader: FirebaseProgramDownloader
) : BuildRobotMvp.Presenter {

    override var view: BuildRobotMvp.View? = null
    override var model: BuildRobotViewModel? = null

    private var configuration: Configuration? = null
    private var controller: Controller? = null
    private var userRobot: UserRobot? = null

    override fun loadBuildSteps(robotId: Int) {
        buildStepInteractor.robotId = robotId
        buildStepInteractor.execute { steps ->
            view?.onBuildStepsLoaded(steps)
        }
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
            configurationInteractor.execute { config ->
                configuration = config
                downloadControllerInfo(config.controller)
            }
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
        controllerInteractor.execute { controller ->
            this.controller = controller
            downloadPrograms(controller.getProgramIds())
        }
    }

    private fun downloadPrograms(ids: List<String>) {
        programsInteractor.programIds = ids
        programsInteractor.execute { programs ->
            downloadProgramFiles(programs)
        }
    }

    private fun downloadProgramFiles(programs: List<Program>) {
        firebaseProgramDownloader.downloadProgramFiles(programs) {
            createLocalObjects(configuration, controller, programs, it)
        }
    }

    private fun createLocalObjects(
        configuration: Configuration?,
        controller: Controller?,
        programs: List<Program>,
        programFiles: List<ProgramLocalFiles>
    ) {
        userRobot?.let { userRobot ->
            saveUserRobotInteractor.userRobot = userRobot
            saveUserRobotInteractor.execute { savedRobot ->
                this.userRobot = savedRobot
                assignConfig(configuration, controller, programs, programFiles)
            }
        }
    }

    private fun assignConfig(
        configuration: Configuration?,
        controller: Controller?,
        programs: List<Program>,
        programFiles: List<ProgramLocalFiles>
    ) {
        assignConfigIntoARobotInteractor.controller = controller
        assignConfigIntoARobotInteractor.configuration = configuration
        assignConfigIntoARobotInteractor.programs = programs
        assignConfigIntoARobotInteractor.programLocalFiles = programFiles
        userRobot?.let { userRobot ->
            assignConfigIntoARobotInteractor.userRobot = userRobot
            assignConfigIntoARobotInteractor.execute { savedRobot ->
                this.userRobot = savedRobot
                view?.onRobotSaved()
            }
        }
    }
}
