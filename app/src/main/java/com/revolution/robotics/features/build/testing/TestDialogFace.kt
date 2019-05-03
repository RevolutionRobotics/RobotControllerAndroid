package com.revolution.robotics.features.build.testing

import com.revolution.robotics.R
import com.revolution.robotics.databinding.DialogTestingBinding
import com.revolution.robotics.views.dialogs.DialogFace

abstract class TestDialogFace : DialogFace<DialogTestingBinding>(R.layout.dialog_testing) {
    abstract val imageResource: Int
    abstract val textResource: Int

    override fun onActivated() {
        super.onActivated()
        binding?.testingImage?.setImageResource(imageResource)
        binding?.testingText?.setText(textResource)
    }
}
