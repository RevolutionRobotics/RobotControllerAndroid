package com.revolution.robotics.features.build.testing

import android.os.Bundle
import com.revolution.robotics.R
import com.revolution.robotics.core.extensions.withArguments
import com.revolution.robotics.core.utils.BundleArgumentDelegate
import com.revolution.robotics.features.build.tips.TipsDialogFace
import com.revolution.robotics.views.dialogs.DialogFace
import com.revolution.robotics.views.dialogs.RoboticsDialog

class DrivetrainTestDialog : TestDialog() {

    companion object {

        private var Bundle.reversed: Boolean by BundleArgumentDelegate.Boolean("reversed")
        private var Bundle.side: String by BundleArgumentDelegate.String("side")

        fun newInstance(portNumber: String, reversed: Boolean, side: String) =
            DrivetrainTestDialog().withArguments { bundle ->
                bundle.portNumber = portNumber
                bundle.reversed = reversed
                bundle.side = side
            }
    }

    override val dialogFaces: List<DialogFace<*>> = listOf(
        TestLoadingDialogFace(this),
        DrivetrainTestDialogFace(this),
        TipsDialogFace(Source.CONFIGURE, this, this)
    )

    override val testFileName = "drivetrain"

    override fun onTestUploaded() {
        activateFace(dialogFaces.first { it is DrivetrainTestDialogFace })
    }

    inner class DrivetrainTestDialogFace(dialog: RoboticsDialog) : TestDialogFace(dialog) {
        override val imageResource: Int = R.drawable.test_drivetrain
        override val textResource: Int = R.string.testing_drivetrain_test

        override fun showTipsFace() {
            activateFace(dialogFaces.first { it is TipsDialogFace })
        }
    }

    override fun generateReplaceablePairs(): List<Pair<String, String>> {
        val replaceablePairs = mutableListOf<Pair<String, String>>()
        arguments?.let { bundle ->
            replaceablePairs.add(REPLACEABLE_TEXT_MOTOR to bundle.portNumber)
            replaceablePairs.add(REPLACEABLE_TEXT_MOTOR_REVERSED to bundle.reversed.toString())
            replaceablePairs.add(REPLACEABLE_TEXT_MOTOR_SIDE to bundle.side)
        }
        return replaceablePairs
    }
}
