package com.revolution.robotics.features.whoToBuild

import com.revolution.robotics.core.domain.PortMapping
import com.revolution.robotics.core.domain.local.BuildStatus
import com.revolution.robotics.core.domain.local.UserRobot
import com.revolution.robotics.core.domain.remote.Configuration
import com.revolution.robotics.core.domain.remote.Robot
import com.revolution.robotics.core.extensions.isEmptyOrNull
import com.revolution.robotics.core.interactor.AssignConfigToRobotInteractor
import com.revolution.robotics.core.interactor.SaveUserRobotInteractor
import com.revolution.robotics.core.interactor.firebase.RobotInteractor
import com.revolution.robotics.core.kodein.utils.ResourceResolver
import com.revolution.robotics.core.utils.Navigator
import com.revolution.robotics.features.build.BuildRobotFragment
import com.revolution.robotics.features.whoToBuild.adapter.RobotsItem
import java.util.Date
import kotlin.math.max

class WhoToBuildPresenter(
    private val robotsInteractor: RobotInteractor,
    private val assignConfigToRobotInteractor: AssignConfigToRobotInteractor,
    private val saveUserRobotInteractor: SaveUserRobotInteractor,
    private val resourceResolver: ResourceResolver,
    private val navigator: Navigator
) :
    WhoToBuildMvp.Presenter {

    override var model: WhoToBuildViewModel? = null
    override var view: WhoToBuildMvp.View? = null

    override fun register(view: WhoToBuildMvp.View, model: WhoToBuildViewModel?) {
        super.register(view, model)
        loadRobots()
    }

    private fun loadRobots() {
        robotsInteractor.execute { response ->
            model?.apply {
                currentPosition.set(0)
                robotsList.value =
                    response.map { robot -> RobotsItem(robot, this@WhoToBuildPresenter) }
                robotsList.value?.firstOrNull()?.isSelected?.set(true)
                updateButtonsVisibility(0)
                view?.onRobotsLoaded()
            }
        }
    }

    override fun onPageSelected(position: Int) {
        model?.run {
            val list = robotsList.value ?: return
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
            if (robotsList.value.isEmptyOrNull()) {
                isPreviousButtonVisible.set(false)
                isNextButtonVisible.set(false)
            } else {
                isPreviousButtonVisible.set(position != 0)
                isNextButtonVisible.set(position != max((robotsList.value?.size ?: 0) - 1, 0))
            }
        }
    }

    override fun nextButtonClick() {
        view?.showNextRobot()
    }

    override fun previousButtonClick() {
        view?.showPreviousRobot()
    }

    override fun onRobotSelected(robot: Robot) {
        val userRobot = UserRobot(
            0,
            robot.id,
            BuildStatus.IN_PROGRESS,
            BuildRobotFragment.DEFAULT_STARTING_INDEX,
            Date(System.currentTimeMillis()),
            0,
            robot.name?.getLocalizedString(resourceResolver) ?: "",
            robot.coverImage,
            robot.description?.getLocalizedString(resourceResolver) ?: ""
        )
        navigator.navigate(WhoToBuildFragmentDirections.toBuildRobot(userRobot))
    }

    override fun onBuildYourOwnSelected() {
        val userRobot = UserRobot(
            buildStatus = BuildStatus.INVALID_CONFIGURATION,
            lastModified = Date(System.currentTimeMillis()),
            name = ""
        )
        saveUserRobotInteractor.userRobot = userRobot
        saveUserRobotInteractor.execute { savedRobot ->
            assignEmptyConfig(savedRobot)
        }
    }

    override fun onDisabledItemClicked(robotsItem: RobotsItem) {
        val index = model?.robotsList?.value?.indexOf(robotsItem) ?: 0
        if (index < model?.robotsList?.value?.indexOfFirst { it.isSelected.get() } ?: 0) {
            view?.showPreviousRobot()
        } else {
            view?.showNextRobot()
        }
    }

    private fun assignEmptyConfig(userRobot: UserRobot) {
        assignConfigToRobotInteractor.userRobot = userRobot
        assignConfigToRobotInteractor.configuration = Configuration(mapping = PortMapping())
        assignConfigToRobotInteractor.controllers = null
        assignConfigToRobotInteractor.programs = emptyList()
        assignConfigToRobotInteractor.execute {
            navigator.navigate(WhoToBuildFragmentDirections.toConfigure(userRobot))
        }
    }
}
