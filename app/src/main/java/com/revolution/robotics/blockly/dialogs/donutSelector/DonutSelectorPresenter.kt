package com.revolution.robotics.blockly.dialogs.donutSelector

class DonutSelectorPresenter : DonutSelectorMvp.Presenter {

    override var view: DonutSelectorMvp.View? = null
    override var model: DonutSelectorViewModel? = null

    override fun onDoneButtonClicked() {
        view?.onDoneButtonClicked()
    }

    override fun onSelectAllClicked() {
        view?.onSelectAllClicked()
    }
}
