package com.revolution.robotics.features.coding.new.robotSelector

import androidx.lifecycle.ViewModel
import com.revolution.robotics.core.domain.local.UserProgram
import com.revolution.robotics.core.domain.local.UserRobot

class RobotSelectorViewModel(private val presenter: RobotSelectorMvp.Presenter) : ViewModel() {

    fun onRobotClicked(robot: UserRobot) {
        presenter.onRobotSelected(robot)
    }
}
