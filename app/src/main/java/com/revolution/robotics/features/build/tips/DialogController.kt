package com.revolution.robotics.features.build.tips

import com.revolution.robotics.core.eventBus.dialog.DialogEvent

interface DialogController {
    fun navigateToCommunity()
    fun publishDialogEvent(event: DialogEvent)
    fun onCancelClicked()
    fun onRetryClicked()
    fun showTips()
}
