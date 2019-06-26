package com.revolution.robotics.blockly.dialogs.optionSelector

import androidx.lifecycle.ViewModel
import com.revolution.robotics.R
import com.revolution.robotics.blockly.dialogs.BlocklyDialogInterface
import com.revolution.robotics.blockly.utils.BlocklyResultHolder
import com.revolution.robotics.views.chippedBox.ChippedBoxConfig
import org.revolution.blockly.view.result.OptionResult

class OptionSelectorViewModel(
    val options: List<Option>,
    private val resultHolder: BlocklyResultHolder,
    private val dialogInterface: BlocklyDialogInterface
) :
    ViewModel() {

    companion object {
        private val BACKGROUND = ChippedBoxConfig.Builder()
            .chipSize(R.dimen.dimen_8dp)
            .backgroundColorResource(R.color.grey_1d)
            .create()
        private val SELECTED_BACKGROUND = ChippedBoxConfig.Builder()
            .chipSize(R.dimen.dimen_8dp)
            .backgroundColorResource(R.color.grey_28)
            .borderColorResource(R.color.white)
            .create()
    }

    fun getBackground(index: Int) =
        if (getOption(index)?.isSelected == true) {
            SELECTED_BACKGROUND
        } else {
            BACKGROUND
        }

    fun getOption(index: Int) =
        if (index <= options.size) {
            options[index - 1]
        } else {
            null
        }

    fun onOptionClicked(index: Int) {
        getOption(index)?.let { option ->
            (resultHolder.result as? OptionResult)?.confirm(option.value)
            dialogInterface.dismiss()
        }
    }
}
