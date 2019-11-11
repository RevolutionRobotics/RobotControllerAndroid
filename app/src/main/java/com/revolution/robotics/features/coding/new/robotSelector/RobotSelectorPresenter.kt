package com.revolution.robotics.features.coding.new.robotSelector

import com.revolution.robotics.core.domain.local.UserRobot
import com.revolution.robotics.core.interactor.GetAllUserRobotsInteractor
import com.revolution.robotics.features.coding.programs.adapter.RobotViewModel

class RobotSelectorPresenter(private val getAllUserRobotsInteractor: GetAllUserRobotsInteractor) :
    RobotSelectorMvp.Presenter {

    override var view: RobotSelectorMvp.View? = null
    override var model: RobotSelectorViewModel? = null

    override fun loadRobots() {
        getAllUserRobotsInteractor.execute { robots ->
            view?.onRobotsLoaded(robots.map { RobotViewModel(it, this) })
        }
    }

    override fun onRobotSelected(robot: UserRobot) {
        view?.onRobotSelected(robot)
    }
}
