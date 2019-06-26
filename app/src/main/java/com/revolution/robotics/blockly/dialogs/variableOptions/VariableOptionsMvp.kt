package com.revolution.robotics.blockly.dialogs.variableOptions

import androidx.lifecycle.ViewModel
import com.revolution.robotics.core.Mvp
import org.revolution.blockly.BlocklyVariable

interface VariableOptionsMvp : Mvp {

    interface View : Mvp.View {
        fun onVariableSelected(variable: BlocklyVariable)
    }

    interface Presenter : Mvp.Presenter<View, ViewModel> {
        fun onVariableSelected(variable: BlocklyVariable)
    }
}
