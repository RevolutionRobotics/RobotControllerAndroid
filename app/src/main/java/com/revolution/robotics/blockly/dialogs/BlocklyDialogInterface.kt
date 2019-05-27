package com.revolution.robotics.blockly.dialogs

import androidx.databinding.ObservableInt

interface BlocklyDialogInterface {
    val hasCloseButton: Boolean
    val hasTitle: Boolean
    val titleResource: ObservableInt

    fun dismiss()
    fun confirmResult(result: String)
}
