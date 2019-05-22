package com.revolution.robotics.features.configure.controllers

import com.revolution.robotics.core.extensions.formatYearMonthDaySlashed
import com.revolution.robotics.core.extensions.isEmptyOrNull
import com.revolution.robotics.core.interactor.GetUserControllersInteractor
import com.revolution.robotics.core.interactor.RemoveUserControllerInteractor
import com.revolution.robotics.core.utils.Navigator
import com.revolution.robotics.features.configure.ConfigureFragmentDirections
import com.revolution.robotics.features.configure.UserConfigurationStorage
import com.revolution.robotics.features.configure.controller.ControllerInfoDialog
import com.revolution.robotics.features.configure.controllers.adapter.ControllersItem
import com.revolution.robotics.features.controllers.ControllerType
import kotlin.math.max

@Suppress("TooManyFunctions")
class ConfigureControllersPresenter(
    private val navigator: Navigator,
    private val controllersInteractor: GetUserControllersInteractor,
    private val userConfigurationStorage: UserConfigurationStorage,
    private val deleteControllerInteractor: RemoveUserControllerInteractor
) : ConfigureControllersMvp.Presenter {

    override var view: ConfigureControllersMvp.View? = null
    override var model: ConfigureControllersViewModel? = null

    override fun register(view: ConfigureControllersMvp.View, model: ConfigureControllersViewModel?) {
        super.register(view, model)
        controllersInteractor.execute({ controllers ->
            model?.controllersList?.set(
                controllers.map { controller ->
                    ControllersItem(
                        controller,
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

    override fun deleteController(controllerId: Int, selectedPosition: Int) {
        deleteControllerInteractor.controllerId = controllerId
        deleteControllerInteractor.execute({}, {
            // TODO Error handling
        })

        model?.controllersList?.apply {
            get()?.toMutableList()?.apply {
                removeAll { it.userController.id == controllerId }
                set(this.toList())
            }
        }
        updateButtonsVisibility(selectedPosition)
        view?.onRobotsChanged()
    }

    override fun onDeleteSelected(item: ControllersItem) {
        view?.showDeleteControllerDialog(item.userController.id)
    }

    override fun onEditSelected(item: ControllersItem) {
        ControllerType.fromId(item.userController.type)?.apply {
            when (this) {
                ControllerType.GAMER -> navigator.navigate(ConfigureFragmentDirections.toSetupGamer())
                ControllerType.MULTITASKER -> navigator.navigate(ConfigureFragmentDirections.toSetupMultitasker())
                ControllerType.DRIVER -> navigator.navigate(ConfigureFragmentDirections.toSetupDriver())
            }
        }
    }

    override fun onInfoSelected(item: ControllersItem) {
        view?.showInfoModal(
            ControllerInfoDialog.newInstance(
                ControllerInfoDialog.ViewModel(
                    title = item.userController.name ?: "",
                    date = item.userController.lastModified.formatYearMonthDaySlashed(),
                    description = item.userController.description ?: ""
                )
            )
        )
    }

    override fun onItemSelectionChanged(item: ControllersItem) {
        model?.controllersList?.get()?.forEach {
            it.isCurrentlyActive.set(it == item)
        }
        userConfigurationStorage.userConfiguration?.controller = item.userController.id
    }
}
