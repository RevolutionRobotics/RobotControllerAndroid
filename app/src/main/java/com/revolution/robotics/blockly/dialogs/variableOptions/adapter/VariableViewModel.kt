package com.revolution.robotics.blockly.dialogs.variableOptions.adapter

import com.revolution.robotics.R
import com.revolution.robotics.blockly.dialogs.variableOptions.VariableOptionsMvp
import com.revolution.robotics.views.chippedBox.ChippedBoxConfig
import org.revolutionrobotics.robotcontroller.blocklysdk.BlocklyVariable

class VariableViewModel(
    private val variable: BlocklyVariable,
    val selected: Boolean,
    private val presenter: VariableOptionsMvp.Presenter
) {
    companion object {
        val BACKGROUND = ChippedBoxConfig.Builder()
            .backgroundColorResource(R.color.black)
            .borderColorResource(R.color.grey_8e)
            .borderSize(R.dimen.dimen_1dp)
            .chipSize(R.dimen.dimen_8dp)
            .clipLeftSide(true)
            .create()

        val SELECTED_BACKGROUND = ChippedBoxConfig.Builder()
            .backgroundColorResource(R.color.black)
            .borderColorResource(R.color.robotics_red)
            .borderSize(R.dimen.dimen_1dp)
            .chipSize(R.dimen.dimen_8dp)
            .clipLeftSide(true)
            .create()
    }

    val name = variable.name
    val background = if (selected) SELECTED_BACKGROUND else BACKGROUND

    fun onVariableClicked() {
        presenter.onVariableSelected(variable)
    }
}
