package com.revolution.robotics.features.build.testing

import com.revolution.robotics.R
import com.revolution.robotics.views.dialogs.DialogFace

class BumperTestDialog : TestDialog() {

    companion object {
        fun newInstance() = BumperTestDialog()
    }

    override val dialogFaces: List<DialogFace<*>> = listOf(BumperTestDialogFace())

    class BumperTestDialogFace : TestDialogFace() {
        override val imageResource: Int = R.drawable.test_bumper
        override val textResource: Int = R.string.testing_bumper_test
    }
}
