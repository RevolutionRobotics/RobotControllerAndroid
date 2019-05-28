package com.revolution.robotics.blockly.dialogs.slider

import com.revolution.robotics.core.Mvp

interface SliderMvp : Mvp {

    interface View : Mvp.View {
        fun onDoneClicked()
    }

    interface Presenter : Mvp.Presenter<View, SliderDialogViewModel> {
        fun onDoneClicked()
    }
}