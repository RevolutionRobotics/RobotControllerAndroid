package com.revolution.robotics.features.build

import com.revolution.robotics.core.domain.local.BuildStatus
import com.revolution.robotics.core.domain.local.UserConfiguration
import com.revolution.robotics.core.domain.local.UserMapping
import com.revolution.robotics.core.domain.local.UserRobot
import com.revolution.robotics.core.domain.remote.BuildStep
import com.revolution.robotics.core.domain.remote.Configuration
import com.revolution.robotics.core.domain.shared.RobotDescriptor
import com.revolution.robotics.core.interactor.GetUserRobotInteractor
import com.revolution.robotics.core.interactor.SaveUserRobotInteractor
import com.revolution.robotics.core.interactor.firebase.BuildStepInteractor
import com.revolution.robotics.core.interactor.firebase.ConfigurationInteractor
import java.util.Date

class BuildRobotPresenter(
    private val buildStepInteractor: BuildStepInteractor,
    private val getUserRobotInteractor: GetUserRobotInteractor,
    private val saveUserRobotInteractor: SaveUserRobotInteractor,
    private val configurationInteractor: ConfigurationInteractor
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
                robot?.configurationId ?: 0,
                robot?.name,
                robot?.coverImage,
                robot?.description
            ),
            false
        )
    }

    private fun createUserConfiguration(configuration: Configuration) =
        UserConfiguration(0, controller = configuration.controller, mappingId = UserMapping().apply {
            M1 = configuration.mapping?.M1
            M2 = configuration.mapping?.M2
            M3 = configuration.mapping?.M3
            M4 = configuration.mapping?.M4
            M5 = configuration.mapping?.M5
            M6 = configuration.mapping?.M6

            S1 = configuration.mapping?.S1
            S2 = configuration.mapping?.S2
            S3 = configuration.mapping?.S3
            S4 = configuration.mapping?.S4
        })

    override fun saveUserRobot(userRobot: UserRobot, createDefaultConfig: Boolean) {
        if (createDefaultConfig) {
            configurationInteractor.configId = userRobot.configurationId
            configurationInteractor.execute({ config ->
                saveUserRobotInteractor.userConfiguration = createUserConfiguration(config)
                saveUserRobotInteractor.userRobot = userRobot
                saveUserRobotInteractor.execute()
            }, {
                // TODO Error handling
            })
        } else {
            saveUserRobotInteractor.userRobot = userRobot
            saveUserRobotInteractor.execute()
        }
    }
}
