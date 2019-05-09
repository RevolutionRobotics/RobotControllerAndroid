package com.revolution.robotics.views.dialogs

import androidx.databinding.ObservableBoolean
import androidx.databinding.ObservableField
import androidx.databinding.ObservableInt
import com.revolution.robotics.R
import com.revolution.robotics.core.extensions.onPropertyChanged
import com.revolution.robotics.views.chippedBox.ChippedBoxConfig

@Suppress("DataClassContainsFunctions")
data class DialogButtonViewModel(
    private val dialogButton: DialogButton,
    private val isFirst: Boolean,
    private val isLast: Boolean
) {
    val text = dialogButton.text
    val icon = dialogButton.icon
    val background: ObservableField<ChippedBoxConfig> = ObservableField()
    val contentColorRes: ObservableInt = ObservableInt()
    val isEnabled: ObservableBoolean = ObservableBoolean(dialogButton.isEnabledOnStart)

    private val factory = ChippedBoxConfig.Builder()
        .chipSize(R.dimen.dialog_chip_size)
        .backgroundColorResource(if (dialogButton.isHighlighted) R.color.grey_28 else R.color.overlay_black_42)
        .chipBottomLeft(isFirst)
        .chipTopRight(isLast)

    init {
        handleEnabledState(dialogButton.isEnabledOnStart)
        isEnabled.onPropertyChanged { handleEnabledState(it) }
    }

    fun onClick() {
        if (isEnabled.get()) {
            dialogButton.onClick.invoke()
        }
    }

    private fun handleEnabledState(enabled: Boolean) {
        if (enabled) {
            if (dialogButton.isHighlighted) {
                toHighlighted()
            } else {
                toDefault()
            }
        } else {
            toDisabled()
        }
    }

    private fun toDisabled() = setState(R.color.grey_8e, R.color.grey_28, R.color.grey_1e)
    private fun toDefault() = setState(R.color.white, R.color.transparent, R.color.overlay_black_42)
    private fun toHighlighted() = setState(R.color.white, R.color.grey_28, R.color.white)

    private fun setState(contentColorRes: Int, backgroundColorRes: Int, borderColorRes: Int) {
        factory.borderColorResource(backgroundColorRes)
            .borderColorResource(borderColorRes)

        background.set(factory.create())
        this.contentColorRes.set(contentColorRes)
    }
}
