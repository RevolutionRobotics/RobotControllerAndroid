package com.revolution.robotics.features.build.tips

import com.revolution.robotics.core.eventBus.dialog.DialogEvent

interface DialogController {
    fun publishDialogEvent(event: DialogEvent)
    fun onCancelClicked()
    fun onRetryClicked()
}
