package com.revolution.robotics.core.dialogs

import com.revolution.robotics.R
import com.revolution.robotics.core.utils.chippedBox.ChippedBoxConfig

@Suppress("DataClassContainsFunctions")
data class DialogButtonViewModel(
    private val dialogButton: DialogButton,
    private val isFirst: Boolean,
    private val isLast: Boolean
) {
    val text = dialogButton.text
    val icon = dialogButton.icon
    val onClick = dialogButton.onClick
    val background: ChippedBoxConfig

    init {
        val factory = ChippedBoxConfig.Builder()
            .chipSize(R.dimen.dialog_chip_size)
            .backgroundColorResource(if (dialogButton.isHighlighted) R.color.grey_28 else R.color.overlay_black_42)
            .chipBottomLeft(isFirst)
            .chipTopRight(isLast)

        if (dialogButton.isHighlighted) {
            factory.borderColorResource(R.color.white)
                .borderSize(R.dimen.dimen_1dp)
        }
        background = factory.create()
    }
}
