package com.revolution.robotics.features.configure.controllers

import com.revolution.robotics.core.Mvp

interface ConfigureControllersMvp : Mvp {
    interface View : Mvp.View {
        fun onRobotsChanged()
        fun showNextRobot()
        fun showPreviousRobot()
    }

    interface Presenter : Mvp.Presenter<View, ConfigureControllersViewModel> {
        fun onPageSelected(position: Int)
        fun nextButtonClick()
        fun previousButtonClick()
        fun onCreateNewClick()
        fun onItemSelected(controllerId: Int)
        fun onEditSelected(controllerId: Int)
        fun onDeleteSelected(controllerId: Int)
        fun onInfoSelected(controllerId: Int)
    }
}
