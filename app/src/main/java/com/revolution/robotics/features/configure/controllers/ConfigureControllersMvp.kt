package com.revolution.robotics.features.configure.controllers

import com.revolution.robotics.core.Mvp
import com.revolution.robotics.features.configure.controller.ControllerInfoDialog
import com.revolution.robotics.features.configure.controllers.adapter.ControllersItem

interface ConfigureControllersMvp : Mvp {
    interface View : Mvp.View {
        fun onRobotsChanged()
        fun showNextRobot()
        fun showPreviousRobot()
        fun showInfoModal(dialog: ControllerInfoDialog)
    }

    interface Presenter : Mvp.Presenter<View, ConfigureControllersViewModel> {
        fun onPageSelected(position: Int)
        fun nextButtonClick()
        fun previousButtonClick()
        fun onCreateNewClick()
        fun onItemSelectionChanged(item: ControllersItem)
        fun onEditSelected(item: ControllersItem)
        fun onDeleteSelected(item: ControllersItem)
        fun onInfoSelected(item: ControllersItem)
    }
}
