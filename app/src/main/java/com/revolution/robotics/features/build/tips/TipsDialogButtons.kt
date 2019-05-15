package com.revolution.robotics.features.build.tips

import com.revolution.robotics.R
import com.revolution.robotics.core.eventBus.dialog.DialogEvent
import com.revolution.robotics.features.build.testing.TestDialog
import com.revolution.robotics.views.dialogs.DialogButton

fun createTipsDialogButtons(source: TestDialog.Source, dialogController: DialogController): MutableList<DialogButton> {
    val firstButton =
        if (source == TestDialog.Source.BUILD) {
            DialogButton(R.string.tips_dialog_button_skip_testing, R.drawable.ic_skip, false, true) {
                dialogController.publishDialogEvent(DialogEvent.SKIP_TESTING)
                dialogController.onCancelClicked()
            }
        } else {
            DialogButton(
                R.string.tips_dialog_button_reconfigure,
                R.drawable.ic_build,
                false,
                true,
                dialogController::onCancelClicked
            )
        }

    return mutableListOf(
        firstButton,
        DialogButton(R.string.tips_dialog_button_community, R.drawable.ic_community) {
            // TODO open community here
        },
        DialogButton(
            R.string.tips_dialog_button_try_again,
            R.drawable.ic_retry,
            true,
            true,
            dialogController::onRetryClicked
        )
    )
}
