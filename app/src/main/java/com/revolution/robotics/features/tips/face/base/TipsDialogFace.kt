package com.revolution.robotics.features.tips.face.base

import com.revolution.robotics.R
import com.revolution.robotics.databinding.DialogTipsBinding
import com.revolution.robotics.views.dialogs.DialogFace

abstract class TipsDialogFace : DialogFace<DialogTipsBinding>(R.layout.dialog_tips) {
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
