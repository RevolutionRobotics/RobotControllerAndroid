package com.revolution.robotics.features.play

class PlayPresenter : PlayMvp.Presenter {

    override var view: PlayMvp.View? = null
    override var model: PlayViewModel? = null

    override fun onJoystickEvent() {
        // TODO handle joystick event
    }

    override fun onButtonPressed(ordinal: Int) {
        // TODO handle button event
    }
}
