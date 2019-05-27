package com.revolution.robotics.blockly.dialogs.directionSelector

class DirectionSelectorPresenter : DirectionSelectorMvp.Presenter {

    override var view: DirectionSelectorMvp.View? = null
    override var model: DirectionSelectorViewModel? = null

    override fun onDirectionSelected(direction: Direction) {
        view?.onDirectionSelected(direction)
    }
}
