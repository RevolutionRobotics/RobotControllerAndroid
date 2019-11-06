package com.revolution.robotics.blockly.dialogs.valueOptions.adapter

import com.revolution.robotics.R
import com.revolution.robotics.blockly.dialogs.valueOptions.ValueOptionsDialog
import com.revolution.robotics.blockly.dialogs.variableOptions.VariableOptionsMvp
import com.revolution.robotics.views.chippedBox.ChippedBoxConfig
import org.revolutionrobotics.blockly.android.BlocklyVariable

class ValueViewModel(
    val value: String,
    val selected: Boolean,
    private val dialog: ValueOptionsDialog
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

    val background = if (selected) SELECTED_BACKGROUND else BACKGROUND

    fun onValueClicked() {
        dialog.onValueSelected(value)
    }
}
