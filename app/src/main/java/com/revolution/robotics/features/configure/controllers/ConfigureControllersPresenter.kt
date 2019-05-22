package com.revolution.robotics.features.configure.controllers

import com.revolution.robotics.core.extensions.formatYearMonthDaySlashed
import com.revolution.robotics.core.extensions.isEmptyOrNull
import com.revolution.robotics.core.interactor.GetUserControllersInteractor
import com.revolution.robotics.core.utils.Navigator
import com.revolution.robotics.features.configure.ConfigureFragmentDirections
import com.revolution.robotics.features.configure.UserConfigurationStorage
import com.revolution.robotics.features.configure.controllers.adapter.ControllersItem
import com.revolution.robotics.features.controllers.ControllerType
import kotlin.math.max

class ConfigureControllersPresenter(
    private val navigator: Navigator,
    private val controllersInteractor: GetUserControllersInteractor,
    private val userConfigurationStorage: UserConfigurationStorage
) : ConfigureControllersMvp.Presenter {
    override var view: ConfigureControllersMvp.View? = null
    override var model: ConfigureControllersViewModel? = null

    override fun register(view: ConfigureControllersMvp.View, model: ConfigureControllersViewModel?) {
        super.register(view, model)
        controllersInteractor.execute({ controllers ->
            model?.controllersList?.set(
                controllers.map { controller ->
                    ControllersItem(
                        controller.id,
                        controller.name ?: "",
                        ControllerType.fromId(controller.type)?.imageResource ?: 0,
                        controller.lastModified.formatYearMonthDaySlashed(),
                        userConfigurationStorage.userConfiguration?.controller == controller.id,
                        this
                    )
                }
            )
        }, {
            // TODO Error handling
        })

        view.onRobotsChanged()
    }

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

    override fun onCreateNewClick() {
        navigator.navigate(ConfigureFragmentDirections.toControllerTypeSelector())
    }

    override fun onItemSelected(controllerId: Int) = Unit

    override fun onEditSelected(controllerId: Int) = Unit

    override fun onDeleteSelected(controllerId: Int) = Unit

    override fun onInfoSelected(controllerId: Int) = Unit
}
