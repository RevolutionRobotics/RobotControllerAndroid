package com.revolution.robotics.blockly.dialogs.directionSelector

import androidx.lifecycle.ViewModel
import com.revolution.robotics.R
import com.revolution.robotics.views.chippedBox.ChippedBoxConfig

class DirectionSelectorViewModel(private val presenter: DirectionSelectorMvp.Presenter) : ViewModel() {

    companion object {
        private val BACKGROUND = ChippedBoxConfig.Builder()
            .backgroundColorResource(R.color.grey_1d)
            .chipSize(R.dimen.dimen_8dp)
            .create()
        private val BACKGROUND_SELECTED = ChippedBoxConfig.Builder()
            .backgroundColorResource(R.color.grey_28)
            .borderColorResource(R.color.white)
            .chipSize(R.dimen.dimen_8dp)
            .create()
    }

    var selectedDirection: Direction? = null

    fun getForwardBackground() =
        if (selectedDirection == Direction.FORWARD) BACKGROUND_SELECTED else BACKGROUND

    fun getBackwardBackground() =
        if (selectedDirection == Direction.BACKWARD) BACKGROUND_SELECTED else BACKGROUND

    fun getTurnLeftBackground() =
        if (selectedDirection == Direction.TURN_LEFT) BACKGROUND_SELECTED else BACKGROUND

    fun getTurnRightBackground() =
        if (selectedDirection == Direction.TURN_RIGHT) BACKGROUND_SELECTED else BACKGROUND

    fun onForwardSelected() =
        presenter.onDirectionSelected(Direction.FORWARD)

    fun onBackwardSelected() =
        presenter.onDirectionSelected(Direction.BACKWARD)

    fun onTurnLeftSelected() =
        presenter.onDirectionSelected(Direction.TURN_LEFT)

    fun onTurnRightSelected() =
        presenter.onDirectionSelected(Direction.TURN_RIGHT)
}