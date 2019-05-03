package com.revolution.robotics.features.testing.face.base

import com.revolution.robotics.R
import com.revolution.robotics.databinding.DialogTestingBinding
import com.revolution.robotics.views.dialogs.DialogFace

abstract class TestingDialogFace : DialogFace<DialogTestingBinding>(R.layout.dialog_testing) {
    abstract val imageResource: Int
    abstract val textResource: Int

    override fun onActivated() {
        super.onActivated()
        binding?.testingImage?.setImageResource(imageResource)
        binding?.testingText?.setText(textResource)
    }
}
