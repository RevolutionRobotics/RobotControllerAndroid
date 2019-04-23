package com.revolution.robotics.features.myRobots

import com.revolution.robotics.core.utils.Navigator

class MyRobotsPresenter(private val navigator: Navigator) : MyRobotsMvp.Presenter {

    override var view: MyRobotsMvp.View? = null
    override var model: MyRobotsViewModel? = null

    override fun navigateToRobots() {
        navigator.navigate(MyRobotsFragmentDirections.toRobots())
    }
}
