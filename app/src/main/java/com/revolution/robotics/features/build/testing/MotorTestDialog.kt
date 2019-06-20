package com.revolution.robotics.features.build.testing

import android.os.Bundle
import com.revolution.robotics.R
import com.revolution.robotics.core.extensions.withArguments
import com.revolution.robotics.core.utils.BundleArgumentDelegate
import com.revolution.robotics.features.build.tips.TipsDialogFace
import com.revolution.robotics.views.dialogs.DialogFace
import com.revolution.robotics.views.dialogs.RoboticsDialog

class MotorTestDialog : TestDialog() {

    companion object {

        private var Bundle.motorDirection: String by BundleArgumentDelegate.String("motorDirection")

        fun newInstance(portNumber: String, direction: String) = MotorTestDialog().withArguments { bundle ->
            bundle.portNumber = portNumber
            bundle.motorDirection = direction
        }
    }

    override val dialogFaces: List<DialogFace<*>> = listOf(
        TestLoadingDialogFace(this),
        MotorTestDialogFace(this),
        TipsDialogFace(Source.CONFIGURE, this, this)
    )

    override val testFileName = "motor"

    override fun onTestUploaded() {
        activateFace(dialogFaces.first { it is MotorTestDialogFace })
    }

    inner class MotorTestDialogFace(dialog: RoboticsDialog) : TestDialogFace(dialog) {
        override val imageResource: Int = R.drawable.test_motor
        override val textResource: Int = R.string.testing_motor_test

        override fun showTipsFace() {
            activateFace(dialogFaces.first { it is TipsDialogFace })
        }
    }

    override fun generateReplaceablePairs(): List<Pair<String, String>> {
        val replaceablePairs = mutableListOf<Pair<String, String>>()
        arguments?.let { bundle ->
            replaceablePairs.add(REPLACEABLE_TEXT_MOTOR to bundle.portNumber)
            replaceablePairs.add(REPLACEABLE_TEXT_MOTOR_DIR to bundle.motorDirection)
        }
        return replaceablePairs
    }
}
