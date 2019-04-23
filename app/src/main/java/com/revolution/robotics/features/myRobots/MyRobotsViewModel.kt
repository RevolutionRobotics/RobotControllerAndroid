package com.revolution.robotics.features.myRobots

import androidx.lifecycle.ViewModel

class MyRobotsViewModel(private val presenter: MyRobotsMvp.Presenter) : ViewModel() {

    fun onBuildNewClicked() {
        presenter.navigateToRobots()
    }
}
