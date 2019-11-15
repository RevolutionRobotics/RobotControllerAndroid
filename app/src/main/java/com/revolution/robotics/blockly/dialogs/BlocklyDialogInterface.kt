package com.revolution.robotics.blockly.dialogs

import androidx.databinding.ObservableField
import androidx.databinding.ObservableInt

interface BlocklyDialogInterface {
    val hasCloseButton: Boolean
    val hasTitle: Boolean
    val titleResource: ObservableInt
    val title: ObservableField<String>

    fun dismissAllowingStateLoss()
}
