package com.revolution.robotics.features.configure.controllers

import com.revolution.robotics.core.extensions.isEmptyOrNull
import kotlin.math.max

class ConfigureControllersPresenter : ConfigureControllersMvp.Presenter {
    override var view: ConfigureControllersMvp.View? = null
    override var model: ConfigureControllersViewModel? = null

    override fun onPageSelected(position: Int) {
        model?.run {
            val list = controllersList.get() ?: return
            if (list.isNotEmpty()) {
                if (currentPosition.get() < list.size) {
                    list[currentPosition.get()].isSelected.set(false)
                }
                list[position].isSelected.set(true)
                currentPosition.set(position)
                updateButtonsVisibility(position)
            }
        }
    }

    private fun updateButtonsVisibility(position: Int) {
        model?.run {
            if (controllersList.get().isEmptyOrNull()) {
                isPreviousButtonVisible.set(false)
                isNextButtonVisible.set(false)
            } else {
                isPreviousButtonVisible.set(position != 0)
                isNextButtonVisible.set(position != max((controllersList.get()?.size ?: 0) - 1, 0))
            }
        }
    }

    override fun nextButtonClick() {
        view?.showNextRobot()
    }

    override fun previousButtonClick() {
        view?.showPreviousRobot()
    }

    override fun onCreateNewClick() = Unit

    override fun onItemSelected(controllerId: Int) = Unit

    override fun onEditSelected(controllerId: Int) = Unit

    override fun onDeleteSelected(controllerId: Int) = Unit

    override fun onInfoSelected(controllerId: Int) = Unit
}
