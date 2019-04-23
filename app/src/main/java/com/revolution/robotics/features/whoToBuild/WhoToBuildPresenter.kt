package com.revolution.robotics.features.whoToBuild

import com.revolution.robotics.features.whoToBuild.adapter.RobotsAdapterItem
import kotlin.math.max

class WhoToBuildPresenter : WhoToBuildMvp.Presenter {

    companion object {
        private const val EXAMPLE_ITEM_RANGE = 5
        private const val START_INDEX: Int = 5
    }

    override var model: WhoToBuildViewModel? = null
    override var view: WhoToBuildMvp.View? = null

    override fun register(view: WhoToBuildMvp.View, model: WhoToBuildViewModel?) {
        super.register(view, model)

        model?.run {
            robotsList.value = (0..EXAMPLE_ITEM_RANGE).map {
                RobotsAdapterItem("Example $it", "${it}h")
            }.toList()

            startIndex.set(START_INDEX)
            robotsList.value?.getOrNull(START_INDEX)?.isSelected?.set(true)
            updateButtonsVisibility(START_INDEX)
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
