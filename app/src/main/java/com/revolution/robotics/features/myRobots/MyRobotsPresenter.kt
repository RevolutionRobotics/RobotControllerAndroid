package com.revolution.robotics.features.myRobots

import com.revolution.robotics.core.domain.local.BuildStatus
import com.revolution.robotics.core.domain.local.UserRobot
import com.revolution.robotics.core.extensions.formatYearMonthDay
import com.revolution.robotics.core.extensions.isEmptyOrNull
import com.revolution.robotics.core.interactor.DeleteRobotInteractor
import com.revolution.robotics.core.interactor.DuplicateUserRobotInteractor
import com.revolution.robotics.core.interactor.GetAllUserRobotsInteractor
import com.revolution.robotics.core.interactor.GetControllerTypeInteractor
import com.revolution.robotics.core.utils.Navigator
import com.revolution.robotics.features.controllers.ControllerType
import com.revolution.robotics.features.myRobots.adapter.MyRobotsItem
import com.revolution.robotics.features.myRobots.info.InfoRobotDialog
import kotlin.math.max

@Suppress("TooManyFunctions")
class MyRobotsPresenter(
    private val getAllUserRobotsInteractor: GetAllUserRobotsInteractor,
    private val deleteRobotInteractor: DeleteRobotInteractor,
    private val getControllerTypeInteractor: GetControllerTypeInteractor,
    private val duplicateUserRobotInteractor: DuplicateUserRobotInteractor,
    private val navigator: Navigator
) : MyRobotsMvp.Presenter {

    override var view: MyRobotsMvp.View? = null
    override var model: MyRobotsViewModel? = null

    private var isEmptyNavigationHappened = false

    override fun register(view: MyRobotsMvp.View, model: MyRobotsViewModel?) {
        super.register(view, model)
        loadRobots()
    }

    private fun loadRobots() {
        getAllUserRobotsInteractor.execute { robots ->
            if (model?.robotsList?.get()?.size != robots.size) {
                model?.currentPosition?.set(0)
            }
            model?.robotsList?.set(robots.map { robot ->
                MyRobotsItem(
                    robot.instanceId,
                    robot,
                    robot.lastModified?.formatYearMonthDay() ?: "",
                    robot.buildStatus != BuildStatus.COMPLETED,
                    this
                )
            }.toMutableList())
            view?.onRobotsChanged()

            if (model?.robotsList?.get()?.isEmpty() == true && !isEmptyNavigationHappened) {
                isEmptyNavigationHappened = true
                navigateToWhoToBuild()
            }
        }
    }

    override fun clearEmptyNavigationFlag() {
        isEmptyNavigationHappened = false
    }

    override fun onPageSelected(position: Int) {
        model?.run {
            val list = robotsList.get() ?: return
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
            if (robotsList.get().isEmptyOrNull()) {
                isPreviousButtonVisible.set(false)
                isNextButtonVisible.set(false)
            } else {
                isPreviousButtonVisible.set(position != 0)
                isNextButtonVisible.set(position != max((robotsList.get()?.size ?: 0) - 1, 0))
            }
        }
    }

    override fun nextButtonClick() {
        view?.showNextRobot()
    }

    override fun previousButtonClick() {
        view?.showPreviousRobot()
    }

    override fun navigateToWhoToBuild() {
        navigator.navigate(MyRobotsFragmentDirections.toWhoToBuild())
    }

    override fun onPlaySelected(configId: Int) {
        getControllerTypeInteractor.configurationId = configId
        getControllerTypeInteractor.execute { type ->
            when (type) {
                ControllerType.GAMER ->
                    navigator.navigate(MyRobotsFragmentDirections.toPlayGamer(configId))
                ControllerType.MULTITASKER ->
                    navigator.navigate(MyRobotsFragmentDirections.toPlayMultitasker(configId))
                ControllerType.DRIVER ->
                    navigator.navigate(MyRobotsFragmentDirections.toPlayDriver(configId))
            }
        }
    }

    override fun onContinueBuildingSelected(robot: UserRobot) {
        if (robot.isCustomBuild() || robot.buildStatus == BuildStatus.INVALID_CONFIGURATION) {
            navigator.navigate(MyRobotsFragmentDirections.toConfigure(robot))
        } else {
            navigator.navigate(MyRobotsFragmentDirections.toBuildRobot(robot))
        }
    }

    override fun onEditSelected(userRobot: UserRobot) {
        navigator.navigate(MyRobotsFragmentDirections.toConfigure(userRobot))
    }

    override fun onMoreInfoClicked(userRobot: UserRobot) {
        view?.showDialog(
            if (userRobot.buildStatus == BuildStatus.COMPLETED ||
                userRobot.buildStatus == BuildStatus.INVALID_CONFIGURATION
            ) {
                InfoRobotDialog.Edit.newInstance(userRobot)
            } else {
                InfoRobotDialog.Normal.newInstance(userRobot)
            }
        )
    }

    override fun deleteRobot(userRobot: UserRobot, selectedPosition: Int) {
        deleteRobotInteractor.id = userRobot.instanceId
        deleteRobotInteractor.configId = userRobot.configurationId
        deleteRobotInteractor.execute()
        model?.robotsList?.apply {
            get()?.removeAll { it.id == userRobot.instanceId }
            notifyChange()
        }
        updateButtonsVisibility(selectedPosition)
        view?.onRobotsChanged()
    }

    override fun duplicateRobot(userRobot: UserRobot) {
        duplicateUserRobotInteractor.currentRobot = userRobot
        duplicateUserRobotInteractor.execute { robot ->
            model?.robotsList?.apply {
                get()?.add(
                    0, MyRobotsItem(
                        robot.instanceId,
                        robot,
                        robot.lastModified?.formatYearMonthDay() ?: "",
                        robot.buildStatus != BuildStatus.COMPLETED,
                        this@MyRobotsPresenter
                    )
                )
                notifyChange()
            }
        }
        model?.currentPosition?.set(0)
        view?.onRobotsChanged()
    }

    override fun onDisabledItemClicked(userRobot: UserRobot) {
        val index = model?.robotsList?.get()?.indexOfFirst { it.robot == userRobot } ?: 0
        if (index < model?.currentPosition?.get() ?: 0) {
            view?.showPreviousRobot()
        } else {
            view?.showNextRobot()
        }
    }
}
