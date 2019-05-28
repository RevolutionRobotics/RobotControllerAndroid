package com.revolution.robotics.blockly.dialogs.slider

class SliderPresenter : SliderMvp.Presenter {

    override var view: SliderMvp.View? = null
    override var model: SliderDialogViewModel? = null

    override fun onDoneClicked() {
        view?.onDoneClicked()
    }
}
