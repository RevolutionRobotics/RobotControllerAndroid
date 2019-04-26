package com.revolution.robotics.features.build.connect.availableRobotsFace

import com.revolution.robotics.features.build.connect.adapter.ConnectRobotItem

// TODO remove this suppress
@Suppress("MagicNumber")
class ConnectPresenter : ConnectMvp.Presenter {

    companion object {
        const val EXAMPLE_AVAILABLE_ROBOTS_RANGE = 5
    }

    override var view: ConnectMvp.View? = null
    override var model: ConnectViewModel? = null

    private var selectedItemId = -1

    override fun register(view: ConnectMvp.View, model: ConnectViewModel?) {
        super.register(view, model)

        // TODO add real data
        model?.availableRobots?.value = listOf(
            ConnectRobotItem(1, "Robot #1", this),
            ConnectRobotItem(2, "Robot #2", this),
            ConnectRobotItem(3, "Robot #3", this),
            ConnectRobotItem(4, "Robot #4", this),
            ConnectRobotItem(5, "Robot #5", this)
        )
    }

    override fun onItemClicked(robotId: Int) {
        getRobotById(selectedItemId)?.setSelected(false)
        getRobotById(robotId)?.setSelected(true)
        selectedItemId = robotId
    }

    private fun getRobotById(robotId: Int) =
        model?.availableRobots?.value?.find { it.robotId == robotId }
}
