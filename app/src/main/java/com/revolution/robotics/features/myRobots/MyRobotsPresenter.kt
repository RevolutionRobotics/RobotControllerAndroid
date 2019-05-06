package com.revolution.robotics.features.myRobots

import com.revolution.robotics.core.domain.local.BuildStatus
import com.revolution.robotics.core.extensions.isEmptyOrNull
import com.revolution.robotics.core.interactor.DeleteRobotInteractor
import com.revolution.robotics.core.interactor.GetAllUserRobotsInteractor
import com.revolution.robotics.core.utils.DateFormatters
import com.revolution.robotics.core.utils.Navigator
import com.revolution.robotics.features.myRobots.adapter.MyRobotsItem
import kotlin.math.max

class MyRobotsPresenter(
    private val getAllUserRobotsInteractor: GetAllUserRobotsInteractor,
    private val deleteRobotInteractor: DeleteRobotInteractor,
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
                model?.robotsList?.value = robots.map { robot ->
                    MyRobotsItem(
                        robot.id,
                        robot.customName ?: "",
                        robot.customDescription ?: "",
                        DateFormatters.yearMonthDay(robot.lastModified),
                        robot.customImage ?: "",
                        robot.buildStatus != BuildStatus.COMPLETED,
                        this
                    )
                }.toMutableList()
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
            if (currentPosition.get() < robotsList.value?.size ?: 0) {
                robotsList.value?.get(currentPosition.get())?.isSelected?.set(false)
            }
            robotsList.value?.get(position)?.isSelected?.set(true)
            currentPosition.set(position)
            updateButtonsVisibility(position)
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

    override fun navigateToWhoToBuild() {
        navigator.navigate(MyRobotsFragmentDirections.toWhoToBuild())
    }

    override fun onPlaySelected(robotId: Int) {
        // Nothing here yet
    }

    override fun onEditSelected(robotId: Int) {
        // Nothing here yet
    }

    override fun onDeleteSelected(robotId: Int) {
        view?.deleteRobot(robotId)
    }

    override fun deleteRobot(robotId: Int) {
        deleteRobotInteractor.id = robotId
        deleteRobotInteractor.execute()
        model?.robotsList?.value?.removeAll { it.id == robotId }
        view?.onRobotsChanged()
    }
}
