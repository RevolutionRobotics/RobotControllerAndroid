package com.revolution.robotics.features.build.testing

import com.revolution.robotics.R
import com.revolution.robotics.core.extensions.withArguments
import com.revolution.robotics.features.build.tips.TipsDialogFace
import com.revolution.robotics.views.dialogs.DialogFace
import com.revolution.robotics.views.dialogs.RoboticsDialog

class UltrasonicTestDialog : TestDialog() {

    companion object {

        fun newInstance(portNumber: String) = UltrasonicTestDialog().withArguments {
            it.portNumber = portNumber
        }
    }

    override val dialogFaces: List<DialogFace<*>> = listOf(
        TestLoadingDialogFace(this),
        UltrasonicTestDialogFace(this),
        TipsDialogFace(Source.CONFIGURE, this, this)
    )

    override val testFileName = "ultrasonic"

    override fun onTestUploaded() {
        activateFace(dialogFaces.first { it is UltrasonicTestDialogFace })
    }

    inner class UltrasonicTestDialogFace(dialog: RoboticsDialog) : TestDialogFace(dialog) {
        override val imageResource: Int = R.drawable.test_ultrasonic
        override val textResource: Int = R.string.testing_ultrasonic_test

        override fun showTipsFace() {
            activateFace(dialogFaces.first { it is TipsDialogFace })
        }
    }

    override fun generateReplaceablePairs(): List<Pair<String, String>> {
        val replaceablePairs = mutableListOf<Pair<String, String>>()
        arguments?.portNumber?.let {
            replaceablePairs.add(REPLACEABLE_TEXT_SENSOR to it)
        }
        return replaceablePairs
    }
}
