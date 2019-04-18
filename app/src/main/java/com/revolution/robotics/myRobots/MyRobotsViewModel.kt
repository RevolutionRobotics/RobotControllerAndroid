package com.revolution.robotics.myRobots

import androidx.lifecycle.ViewModel

class MyRobotsViewModel(private val presenter: MyRobotsMvp.Presenter) : ViewModel() {

    fun onBuildNewClicked() {
        presenter.navigateToRobots()
    }
}
