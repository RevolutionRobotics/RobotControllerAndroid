package com.revolution.robotics.features.build.testing

import com.revolution.robotics.R
import com.revolution.robotics.features.build.tips.BumperTipsDialogFace
import com.revolution.robotics.views.dialogs.DialogFace
import com.revolution.robotics.views.dialogs.RoboticsDialog

sealed class BumperTestDialog(source: Source) : TestDialog() {

    override val dialogFaces: List<DialogFace<*>> = listOf(
        BumperTestDialogFace(this),
        BumperTipsDialogFace(source, this)
    )

    inner class BumperTestDialogFace(dialog: RoboticsDialog) : TestDialogFace(dialog) {
        override val imageResource: Int = R.drawable.test_bumper
        override val textResource: Int = R.string.testing_bumper_test

        override fun showTipsFace() {
            activateFace(dialogFaces.first { it is BumperTipsDialogFace })
        }
    }

    class Build : BumperTestDialog(Source.BUILD)
    class Configure : BumperTestDialog(Source.CONFIGURE)
}
