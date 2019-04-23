package com.revolution.robotics.features.whoToBuild

import com.revolution.robotics.core.extensions.isEmptyOrNull
import com.revolution.robotics.core.interactor.RobotInteractor
import com.revolution.robotics.features.whoToBuild.adapter.RobotItem
import kotlin.math.max

class WhoToBuildPresenter(private val robotsInteractor: RobotInteractor) : WhoToBuildMvp.Presenter {

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
                    robotsList.value = response.map {
                        RobotItem(it.name ?: "", it.buildTime ?: "", it.coverImage ?: "")
                    }
                    robotsList.value?.firstOrNull()?.isSelected?.set(true)
                    updateButtonsVisibility(0)
                    view?.onRobotsLoaded()
                }
            }, onError = { error ->
                // TODO add error handling
            })
    }

    override fun onPageSelected(position: Int) {
        model?.run {
            robotsList.value?.get(currentPosition.get())?.isSelected?.set(false)
            robotsList.value?.get(position)?.isSelected?.set(true)
            currentPosition.set(position)

            if (robotsList.value.isEmptyOrNull()) {
                isPreviousButtonVisible.set(false)
                isNextButtonVisible.set(false)
            } else {
                updateButtonsVisibility(position)
            }
        }
    }

    private fun updateButtonsVisibility(position: Int) {
        model?.run {
            isPreviousButtonVisible.set(position != 0)
            isNextButtonVisible.set(position != max((robotsList.value?.size ?: 0) - 1, 0))
        }
    }

    override fun nextButtonClick() {
        view?.showNextRobot()
    }

    override fun previousButtonClick() {
        view?.showPreviousRobot()
    }
}
