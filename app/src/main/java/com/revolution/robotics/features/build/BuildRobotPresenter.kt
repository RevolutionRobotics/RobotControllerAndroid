package com.revolution.robotics.features.build

import com.revolution.robotics.core.interactor.BuildStepInteractor

class BuildRobotPresenter(private val buildStepInteractor: BuildStepInteractor) : BuildRobotMvp.Presenter {

    override var view: BuildRobotMvp.View? = null
    override var model: BuildRobotViewModel? = null

    override fun loadBuildSteps(robotId: Int) {
        buildStepInteractor.robotId = robotId
        buildStepInteractor.execute(
            onResponse = { steps ->
                view?.onBuildStepsLoaded(steps)
            },
            onError = { error ->
                error.printStackTrace()
                // TODO add error handling
            }
        )
    }
}