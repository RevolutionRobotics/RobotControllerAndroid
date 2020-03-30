package com.revolution.robotics.features.build

import com.revolution.robotics.core.domain.local.UserRobot
import com.revolution.robotics.core.eventBus.dialog.DialogEvent
import com.revolution.robotics.core.eventBus.dialog.DialogEventBus
import com.revolution.robotics.core.interactor.SaveUserRobotInteractor
import com.revolution.robotics.core.interactor.firebase.BuildStepInteractor
import com.revolution.robotics.core.utils.CreateRobotInstanceHelper
import com.revolution.robotics.core.utils.Navigator
import com.revolution.robotics.features.shared.ErrorHandler

class BuildRobotPresenter(
    private val buildStepInteractor: BuildStepInteractor,
    private val saveUserRobotInteractor: SaveUserRobotInteractor,
    private val createRobotInstanceHelper: CreateRobotInstanceHelper,
    private val navigator: Navigator,
    private val dialogEventBus: DialogEventBus,
    private val errorHandler: ErrorHandler
) : BuildRobotMvp.Presenter {

    override var view: BuildRobotMvp.View? = null
    override var model: BuildRobotViewModel? = null

    private var userRobot: UserRobot? = null

    override fun loadBuildSteps(robotId: String) {
        buildStepInteractor.robotId = robotId
        buildStepInteractor.execute { steps ->
            view?.onBuildStepsLoaded(steps)
        }
    }

    override fun letsDrive() {
        userRobot?.let { robot ->
            startPlayFragment(robot)
        }
    }

    private fun startPlayFragment(robot: UserRobot) {
        navigator.navigate(BuildRobotFragmentDirections.toPlay(robot.id))
    }

    override fun saveUserRobot(userRobot: UserRobot, createDefaultConfig: Boolean) {
        if (createDefaultConfig) {
            view?.onRobotSaveStarted()
            this.userRobot = userRobot
            createRobotInstanceHelper.setupConfigFromFirebase(userRobot,
                onSuccess = { savedRobot ->
                    this.userRobot = savedRobot
                    dialogEventBus.publish(DialogEvent.ROBOT_CREATED)
                }, onError = {
                    errorHandler.onError(it)
                    dialogEventBus.publish(DialogEvent.ROBOT_CREATE_ERROR)
                })
        } else {
            saveUserRobotInteractor.userRobot = userRobot
            saveUserRobotInteractor.execute { savedRobot ->
                this.userRobot = savedRobot
                view?.goBack()
            }
        }
    }
}
