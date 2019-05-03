package com.revolution.robotics.features.build.tips

import com.revolution.robotics.R
import com.revolution.robotics.databinding.DialogTipsBinding
import com.revolution.robotics.views.dialogs.DialogFace
import com.revolution.robotics.views.dialogs.RoboticsDialog

abstract class TipsDialogFace(dialog: RoboticsDialog? = null) :
    DialogFace<DialogTipsBinding>(R.layout.dialog_tips, dialog) {

    abstract val bulletCharacter: Char
    abstract val tipsList: List<Int>

    override fun onActivated() {
        super.onActivated()
        binding?.tipsText?.apply {
            text = tipsList.map { "$bulletCharacter ${context.getString(it)}" }
                .toList()
                .joinToString(separator = "\n")
        }
    }
}
