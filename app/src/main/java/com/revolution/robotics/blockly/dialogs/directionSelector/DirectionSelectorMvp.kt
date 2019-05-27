package com.revolution.robotics.blockly.dialogs.directionSelector

import com.revolution.robotics.core.Mvp

interface DirectionSelectorMvp : Mvp {

    interface View : Mvp.View {
        fun onDirectionSelected(direction: Direction)
    }

    interface Presenter : Mvp.Presenter<View, DirectionSelectorViewModel> {
        fun onDirectionSelected(direction: Direction)
    }
}
