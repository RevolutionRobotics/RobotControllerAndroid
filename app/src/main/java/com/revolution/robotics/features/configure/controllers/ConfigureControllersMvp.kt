package com.revolution.robotics.features.configure.controllers

import com.revolution.robotics.core.Mvp
import com.revolution.robotics.features.configure.controller.ControllerInfoDialog
import com.revolution.robotics.features.configure.controllers.adapter.ControllersItem

interface ConfigureControllersMvp : Mvp {
    interface View : Mvp.View {
        fun onControllersChanged(selectedPosition: Int)
        fun showNextRobot()
        fun showPreviousRobot()
        fun showInfoModal(dialog: ControllerInfoDialog)
        fun showDeleteControllerDialog(controllerId: Int)
    }

    @Suppress("ComplexInterface")
    interface Presenter : Mvp.Presenter<View, ConfigureControllersViewModel> {
        fun clearEmptyNavigationFlag()
        fun loadControllers(robotId: Int)
        fun onPageSelected(position: Int)
        fun nextButtonClick()
        fun previousButtonClick()
        fun onCreateNewClick()
        fun deleteController(controllerId: Int, selectedPosition: Int)
        fun onItemSelectionChanged(item: ControllersItem)
        fun onEditSelected(item: ControllersItem)
        fun onDeleteSelected(item: ControllersItem)
        fun onInfoSelected(item: ControllersItem)
        fun onDisabledItemCLicked(item: ControllersItem)
    }
}
