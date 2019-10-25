package com.revolution.robotics.blockly.dialogs.dialpad

import androidx.databinding.ObservableField
import androidx.lifecycle.ViewModel
import com.revolution.robotics.R
import com.revolution.robotics.blockly.dialogs.BlocklyDialogInterface
import com.revolution.robotics.blockly.utils.BlocklyResultHolder
import com.revolution.robotics.views.chippedBox.ChippedBoxConfig
import org.revolutionrobotics.blockly.android.view.result.DialpadResult

class DialpadViewModel(
    private val blocklyResultHolder: BlocklyResultHolder,
    private val dialogInterface: BlocklyDialogInterface
) : ViewModel() {

    val result = ObservableField<String>()

    val inputBackground = ChippedBoxConfig.Builder()
        .chipSize(R.dimen.dimen_4dp)
        .backgroundColorResource(R.color.grey_6d)
        .chipBottomRight(true)
        .chipTopLeft(true)
        .create()
    val numberBackground = ChippedBoxConfig.Builder()
        .chipSize(R.dimen.dimen_6dp)
        .backgroundColorResource(R.color.grey_1d)
        .create()
    val dotBackground = ChippedBoxConfig.Builder()
        .chipSize(R.dimen.dimen_6dp)
        .backgroundColorResource(R.color.grey_28)
        .borderColorResource(R.color.grey_1d)
        .create()
    val okBackground = ChippedBoxConfig.Builder()
        .chipSize(R.dimen.dimen_6dp)
        .backgroundColorResource(R.color.white)
        .create()

    fun onCharacterClicked(character: String) {
        val nextResult =
            if (result.get() == "0" && character != ".") {
                character
            } else {
                "${result.get() ?: ""}$character"
            }
        if (isValidNumber(nextResult)) {
            result.set(nextResult)
        }
    }

    fun onBackspaceClicked() {
        val currentText = result.get()
        if (!currentText.isNullOrEmpty()) {
            result.set(currentText.substring(0, currentText.length - 1))
        }
    }

    fun onOkClicked() {
        val doubleResult = if (result.get().isNullOrEmpty()) 0.0 else result.get()?.toDouble() ?: 0.0
        (blocklyResultHolder.result as? DialpadResult)?.confirm(doubleResult)
        dialogInterface.dismissAllowingStateLoss()
    }

    @Suppress("SwallowedException")
    private fun isValidNumber(number: String) =
        try {
            number.toDouble()
            !number.startsWith("0") || number == "0" || number.startsWith("0.")
        } catch (exception: NumberFormatException) {
            false
        }
}
