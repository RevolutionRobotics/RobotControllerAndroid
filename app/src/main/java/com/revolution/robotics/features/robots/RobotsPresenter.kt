package com.revolution.robotics.features.robots

import com.revolution.robotics.features.robots.adapter.RobotsAdapterItem
import kotlin.math.max

class RobotsPresenter : RobotsMvp.Presenter {

    companion object {
        private const val EXAMPLE_ITEM_RANGE = 5
        private const val EXAMPLE_START_INDEX: Int = 5
    }

    override var model: RobotsViewModel? = null
    override var view: RobotsMvp.View? = null

    override fun register(view: RobotsMvp.View, model: RobotsViewModel?) {
        super.register(view, model)

        model?.run {
            robotsList.value = (0..EXAMPLE_ITEM_RANGE).map {
                RobotsAdapterItem("Example $it", "${it}h")
            }.toList()

            startIndex.set(EXAMPLE_START_INDEX)
            robotsList.value?.get(EXAMPLE_START_INDEX)?.isSelected?.set(true)
            updateButtonsVisibility(EXAMPLE_START_INDEX)
        }
    }

    override fun onPageSelected(position: Int) {
        model?.run {
            robotsList.value?.get(currentPosition.get())?.isSelected?.set(false)
            robotsList.value?.get(position)?.isSelected?.set(true)
            currentPosition.set(position)
        }

        updateButtonsVisibility(position)
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
