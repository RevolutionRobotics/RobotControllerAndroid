package com.revolution.robotics.features.build

import com.revolution.robotics.core.domain.local.UserConfiguration
import com.revolution.robotics.core.domain.local.UserMapping
import com.revolution.robotics.core.domain.local.UserRobot
import com.revolution.robotics.core.domain.remote.Configuration
import com.revolution.robotics.core.interactor.SaveUserRobotInteractor
import com.revolution.robotics.core.interactor.firebase.BuildStepInteractor
import com.revolution.robotics.core.interactor.firebase.ConfigurationInteractor

class BuildRobotPresenter(
    private val buildStepInteractor: BuildStepInteractor,
    private val saveUserRobotInteractor: SaveUserRobotInteractor,
    private val configurationInteractor: ConfigurationInteractor
) : BuildRobotMvp.Presenter {

    override var view: BuildRobotMvp.View? = null
    override var model: BuildRobotViewModel? = null

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

    private fun createUserConfiguration(configuration: Configuration) =
        UserConfiguration(0, configuration.controller, UserMapping().apply {
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
            configurationInteractor.execute(
                onResponse = { config ->
                    saveUserRobotInteractor.userConfiguration = createUserConfiguration(config)
                    saveUserRobotInteractor.userRobot = userRobot
                    saveUserRobotInteractor.execute()
                },
                onError = { error ->
                    // TODO Error handling
                })
        } else {
            saveUserRobotInteractor.userRobot = userRobot
            saveUserRobotInteractor.execute()
        }
    }
}
