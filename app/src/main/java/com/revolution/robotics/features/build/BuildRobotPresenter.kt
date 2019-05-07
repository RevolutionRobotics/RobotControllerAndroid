package com.revolution.robotics.features.build

import com.revolution.robotics.core.domain.local.BuildStatus
import com.revolution.robotics.core.domain.local.UserRobot
import com.revolution.robotics.core.domain.remote.BuildStep
import com.revolution.robotics.core.domain.shared.RobotDescriptor
import com.revolution.robotics.core.interactor.GetUserRobotInteractor
import com.revolution.robotics.core.interactor.SaveUserRobotInteractor
import com.revolution.robotics.core.interactor.firebase.BuildStepInteractor
import java.util.Date

class BuildRobotPresenter(
    private val buildStepInteractor: BuildStepInteractor,
    private val getUserRobotInteractor: GetUserRobotInteractor,
    private val saveUserRobotInteractor: SaveUserRobotInteractor
) : BuildRobotMvp.Presenter {

    override var view: BuildRobotMvp.View? = null
    override var model: BuildRobotViewModel? = null

    override fun loadUserRobot(robotId: Int) {
        getUserRobotInteractor.robotId = robotId
        getUserRobotInteractor.buildStatus = BuildStatus.IN_PROGRESS
        getUserRobotInteractor.execute(
            onResponse = { result -> view?.onUserRobotLoaded(result) },
            onError = { error ->
                // TODO add error handling
                error.printStackTrace()
            }
        )
    }

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

    override fun createNewRobot(robot: RobotDescriptor?, currentBuildStep: BuildStep?) {
        saveUserRobot(
            UserRobot(
                0,
                robot?.id ?: 0,
                BuildStatus.IN_PROGRESS,
                currentBuildStep?.stepNumber ?: BuildRobotFragment.DEFAULT_STARTING_INDEX,
                Date(System.currentTimeMillis()),
                null,
                robot?.name,
                robot?.coverImage,
                robot?.description
            )
        )
    }

    override fun saveUserRobot(userRobot: UserRobot) {
        saveUserRobotInteractor.userRobot = userRobot
        saveUserRobotInteractor.execute()
    }
}
