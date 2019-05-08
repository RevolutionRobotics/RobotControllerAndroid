package com.revolution.robotics.features.whoToBuild

import com.revolution.robotics.core.domain.remote.Robot
import com.revolution.robotics.core.extensions.isEmptyOrNull
import com.revolution.robotics.core.interactor.firebase.RobotInteractor
import com.revolution.robotics.core.utils.Navigator
import com.revolution.robotics.features.whoToBuild.adapter.RobotsItem
import kotlin.math.max

class WhoToBuildPresenter(
    private val robotsInteractor: RobotInteractor,
    private val navigator: Navigator
) : WhoToBuildMvp.Presenter {

    override var model: WhoToBuildViewModel? = null
    override var view: WhoToBuildMvp.View? = null

    override fun register(view: WhoToBuildMvp.View, model: WhoToBuildViewModel?) {
        super.register(view, model)
        loadRobots()
    }

    private fun loadRobots() {
        robotsInteractor.execute(
            onResponse = { response ->
                model?.apply {
                    currentPosition.set(0)
                    robotsList.value = response.map { robot -> RobotsItem(robot, this@WhoToBuildPresenter) }
                    robotsList.value?.firstOrNull()?.isSelected?.set(true)
                    updateButtonsVisibility(0)
                    view?.onRobotsLoaded()
                }
            }, onError = { error ->
                // TODO add error handling
                error.printStackTrace()
            })
    }

    override fun onPageSelected(position: Int) {
        model?.run {
            robotsList.value?.get(currentPosition.get())?.isSelected?.set(false)
            robotsList.value?.get(position)?.isSelected?.set(true)
            currentPosition.set(position)
            updateButtonsVisibility(position)
        }
    }

    private fun updateButtonsVisibility(position: Int) {
        model?.run {
            if (robotsList.value.isEmptyOrNull()) {
                isPreviousButtonVisible.set(false)
                isNextButtonVisible.set(false)
            } else {
                isPreviousButtonVisible.set(position != 0)
                isNextButtonVisible.set(position != max((robotsList.value?.size ?: 0) - 1, 0))
            }
        }
    }

    override fun nextButtonClick() {
        view?.showNextRobot()
    }

    override fun previousButtonClick() {
        view?.showPreviousRobot()
    }

    override fun onRobotSelected(robot: Robot) {
        navigator.navigate(WhoToBuildFragmentDirections.toBuildRobot(robot))
    }
}
