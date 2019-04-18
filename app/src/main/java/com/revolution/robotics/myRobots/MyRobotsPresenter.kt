package com.revolution.robotics.myRobots

import com.revolution.robotics.core.navigation.Navigator

class MyRobotsPresenter(private val navigator: Navigator) : MyRobotsMvp.Presenter {

    override var view: MyRobotsMvp.View? = null
    override var model: MyRobotsViewModel? = null

    override fun navigateToRobots() {
        navigator.navigate(MyRobotsFragmentDirections.toRobots())
    }
}
