package com.revolution.robotics.features.build.tips

import com.revolution.robotics.R
import com.revolution.robotics.features.build.testing.TestDialog
import com.revolution.robotics.views.dialogs.DialogButton

fun createTipsDialogButtons(source: TestDialog.Source, dialogController: DialogController): List<DialogButton> {
    val (text, icon) =
        if (source == TestDialog.Source.BUILD) {
            R.string.tips_dialog_button_skip_testing to R.drawable.ic_skip
        } else {
            R.string.tips_dialog_button_reconfigure to R.drawable.ic_build
        }

    return listOf(
        DialogButton(text, icon, false, dialogController::onCancelClicked),
        DialogButton(R.string.tips_dialog_button_community, R.drawable.ic_community) {
            // TODO open community here
        },
        DialogButton(R.string.tips_dialog_button_try_again, R.drawable.ic_retry, true, dialogController::onRetryClicked)
    )
}
