package com.revolution.robotics.blockly.dialogs.dialpad

import androidx.databinding.ObservableField
import androidx.lifecycle.ViewModel
import com.revolution.robotics.R
import com.revolution.robotics.blockly.dialogs.BlocklyDialogInterface
import com.revolution.robotics.views.chippedBox.ChippedBoxConfig

class DialpadViewModel(private val dialogInterface: BlocklyDialogInterface) : ViewModel() {

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
        result.get()?.let { dialogInterface.confirmPromptResult(it) }
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
