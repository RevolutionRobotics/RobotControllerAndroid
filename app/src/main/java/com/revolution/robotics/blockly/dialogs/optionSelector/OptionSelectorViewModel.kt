package com.revolution.robotics.blockly.dialogs.optionSelector

import androidx.lifecycle.ViewModel
import com.revolution.robotics.R
import com.revolution.robotics.blockly.dialogs.BlocklyDialogInterface
import com.revolution.robotics.views.chippedBox.ChippedBoxConfig
import kotlinx.android.parcel.IgnoredOnParcel

class OptionSelectorViewModel(val options: List<Option>, private val dialogInterface: BlocklyDialogInterface) :
    ViewModel() {

    companion object {
        private val BACKGROUND = ChippedBoxConfig.Builder()
            .chipSize(R.dimen.dimen_8dp)
            .backgroundColorResource(R.color.grey_1d)
            .create()
    }

    @IgnoredOnParcel
    val background = BACKGROUND

    fun getOption(index: Int) =
        if (index <= options.size) {
            options[index - 1]
        } else {
            null
        }

    fun onOptionClicked(index: Int) {
        getOption(index)?.let { option -> dialogInterface.confirmResult(option.value) }
    }
}