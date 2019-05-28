package com.revolution.robotics.blockly.dialogs.donutSelector

import com.revolution.robotics.core.Mvp

interface DonutSelectorMvp : Mvp {

    interface View : Mvp.View {
        fun onSelectAllClicked()
        fun onDoneButtonClicked()
    }

    interface Presenter : Mvp.Presenter<View, DonutSelectorViewModel> {
        fun onSelectAllClicked()
        fun onDoneButtonClicked()
    }
}
