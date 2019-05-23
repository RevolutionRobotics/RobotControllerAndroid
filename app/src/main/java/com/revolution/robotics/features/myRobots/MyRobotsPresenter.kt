package com.revolution.robotics.features.myRobots

import com.revolution.robotics.core.domain.local.BuildStatus
import com.revolution.robotics.core.domain.local.UserRobot
import com.revolution.robotics.core.extensions.formatYearMonthDaySlashed
import com.revolution.robotics.core.extensions.isEmptyOrNull
import com.revolution.robotics.core.interactor.DeleteRobotInteractor
import com.revolution.robotics.core.interactor.GetAllUserRobotsInteractor
import com.revolution.robotics.core.interactor.GetControllerTypeInteractor
import com.revolution.robotics.core.utils.Navigator
import com.revolution.robotics.features.controllers.ControllerType
import com.revolution.robotics.features.myRobots.adapter.MyRobotsItem
import kotlin.math.max

@Suppress("TooManyFunctions")
class MyRobotsPresenter(
    private val getAllUserRobotsInteractor: GetAllUserRobotsInteractor,
    private val deleteRobotInteractor: DeleteRobotInteractor,
    private val getControllerTypeInteractor: GetControllerTypeInteractor,
    private val navigator: Navigator
) : MyRobotsMvp.Presenter {
    override var view: MyRobotsMvp.View? = null
    override var model: MyRobotsViewModel? = null

    override fun register(view: MyRobotsMvp.View, model: MyRobotsViewModel?) {
        super.register(view, model)
        loadRobots()
    }

    private fun loadRobots() {
        getAllUserRobotsInteractor.execute(
            onResponse = { robots ->
                model?.robotsList?.set(robots.map { robot ->
                    MyRobotsItem(
                        robot.instanceId,
                        robot,
                        robot.lastModified?.formatYearMonthDaySlashed() ?: "",
                        robot.buildStatus != BuildStatus.COMPLETED,
                        this
                    )
                }.toMutableList())
                view?.onRobotsChanged()
            },
            onError = { error ->
                // TODO add error handling
                error.printStackTrace()
            }
        )
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
        getControllerTypeInteractor.execute({ type ->
            when (type) {
                ControllerType.GAMER -> navigator.navigate(MyRobotsFragmentDirections.toPlayGamer())
                ControllerType.MULTITASKER -> navigator.navigate(MyRobotsFragmentDirections.toPlayMultitasker())
                ControllerType.DRIVER -> navigator.navigate(MyRobotsFragmentDirections.toPlayDriver())
            }
        }, {
            // TODO Error handling
        })
    }

    override fun onContinueBuildingSelected(robot: UserRobot) {
        if (robot.isCustomBuild()) {
            navigator.navigate(MyRobotsFragmentDirections.toConfigure(robot))
        } else {
            navigator.navigate(MyRobotsFragmentDirections.toBuildRobot(robot))
        }
    }

    override fun onEditSelected(userRobot: UserRobot) {
        navigator.navigate(MyRobotsFragmentDirections.toConfigure(userRobot))
    }

    override fun onDeleteSelected(robotId: Int) {
        view?.deleteRobot(robotId)
    }

    override fun deleteRobot(robotId: Int, selectedPosition: Int) {
        deleteRobotInteractor.id = robotId
        deleteRobotInteractor.execute()
        model?.robotsList?.apply {
            get()?.removeAll { it.id == robotId }
            notifyChange()
        }
        updateButtonsVisibility(selectedPosition)
        view?.onRobotsChanged()
    }
}
