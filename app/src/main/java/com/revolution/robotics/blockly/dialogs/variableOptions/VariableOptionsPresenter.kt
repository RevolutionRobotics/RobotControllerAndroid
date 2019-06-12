package com.revolution.robotics.blockly.dialogs.variableOptions

import androidx.lifecycle.ViewModel

class VariableOptionsPresenter : VariableOptionsMvp.Presenter {

    override var view: VariableOptionsMvp.View? = null
    override var model: ViewModel? = null

    override fun onVariableSelected(variableKey: String) {
        view?.onVariableSelected(variableKey)
    }
}
