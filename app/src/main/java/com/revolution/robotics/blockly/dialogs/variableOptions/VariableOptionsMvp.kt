package com.revolution.robotics.blockly.dialogs.variableOptions

import androidx.lifecycle.ViewModel
import com.revolution.robotics.core.Mvp

interface VariableOptionsMvp : Mvp {

    interface View : Mvp.View {
        fun onVariableSelected(variableKey: String)
    }

    interface Presenter : Mvp.Presenter<View, ViewModel> {
        fun onVariableSelected(variableKey: String)
    }
}
