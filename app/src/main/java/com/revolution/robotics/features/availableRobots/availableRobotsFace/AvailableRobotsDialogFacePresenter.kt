package com.revolution.robotics.features.availableRobots.availableRobotsFace

import com.revolution.robotics.features.availableRobots.adapter.AvailableRobotsItem

const val EXAMPLE_AVAILABLE_ROBOTS_RANGE = 5

class AvailableRobotsDialogFacePresenter : AvailableRobotsDialogFaceMvp.Presenter {
    override var view: AvailableRobotsDialogFaceMvp.View? = null
    override var model: AvailableRobotsDialogFaceViewModel? = null

    private var selectedItemIdField: Any? = null

    override fun register(view: AvailableRobotsDialogFaceMvp.View, model: AvailableRobotsDialogFaceViewModel?) {
        super.register(view, model)

        model?.let { m ->
            m.availableRobots.value =
                (0..EXAMPLE_AVAILABLE_ROBOTS_RANGE).toList().map { AvailableRobotsItem("RobotID $it", "Robot $it") }
                    .toList()
        }
    }

    override fun onItemClick(idField: Any) {
        model?.let { m ->
            val previousItem = m.availableRobots.value?.find { it.idField == selectedItemIdField }
            val nextItem = m.availableRobots.value?.find { it.idField == idField }

            if (selectedItemIdField == idField) {
                selectedItemIdField = null
                nextItem?.isSelected?.set(false)
            } else {
                selectedItemIdField = idField
                previousItem?.isSelected?.set(false)
                nextItem?.isSelected?.set(true)
            }
        }
    }
}
