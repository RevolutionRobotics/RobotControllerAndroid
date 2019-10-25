package com.revolution.robotics.blockly.dialogs.variableOptions

import androidx.lifecycle.ViewModel
import org.revolutionrobotics.blockly.android.BlocklyVariable

class VariableOptionsPresenter : VariableOptionsMvp.Presenter {

    override var view: VariableOptionsMvp.View? = null
    override var model: ViewModel? = null

    override fun onVariableSelected(variable: BlocklyVariable) {
        view?.onVariableSelected(variable)
    }
}
