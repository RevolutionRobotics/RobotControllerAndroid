package com.revolution.robotics.features.myRobots

import com.revolution.robotics.core.extensions.isEmptyOrNull
import com.revolution.robotics.core.utils.Navigator
import com.revolution.robotics.features.myRobots.adapter.MyRobotsItem
import kotlin.math.max

const val EXAMPLE_MY_ROBOTS_RANGE = 5

class MyRobotsPresenter(private val navigator: Navigator) : MyRobotsMvp.Presenter {
    override var view: MyRobotsMvp.View? = null
    override var model: MyRobotsViewModel? = null

    override fun register(view: MyRobotsMvp.View, model: MyRobotsViewModel?) {
        super.register(view, model)
        loadRobots()
    }

    private fun loadRobots() {
        model?.robotsList?.value = (0..EXAMPLE_MY_ROBOTS_RANGE).map { idNumber ->
            MyRobotsItem(
                id = idNumber,
                name = "My Robot #$idNumber",
                description = "This text is here because i was not allowed to use the cat placeholder...",
                iconDescription = "2019/04/30",
                imageUrl = "gs://revolutionrobotics-afeb5.appspot.com/tobbie-diy-robot-build-your-own-gadget.png",
                isUnderConstruction = idNumber % 2 == 0,
                presenter = this@MyRobotsPresenter
            )
        }
        view?.onRobotsLoaded()
    }

    override fun onPageSelected(position: Int) {
        model?.run {
            robotsList.value?.get(currentPosition.get())?.isSelected?.set(false)
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

    override fun onPlaySelected(id: Int) {
        // TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onEditSelected(id: Int) {
        // TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onDeleteSelected(id: Int) {
        // TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}
