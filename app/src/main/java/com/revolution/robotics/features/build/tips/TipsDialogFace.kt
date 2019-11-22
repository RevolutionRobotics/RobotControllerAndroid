package com.revolution.robotics.features.build.tips

import com.revolution.robotics.R
import com.revolution.robotics.databinding.DialogTipsBinding
import com.revolution.robotics.features.build.testing.TestDialog
import com.revolution.robotics.views.dialogs.DialogFace
import com.revolution.robotics.views.dialogs.RoboticsDialog

open class TipsDialogFace(
    source: TestDialog.Source?,
    dialogController: DialogController?,
    dialog: RoboticsDialog? = null
) :
    DialogFace<DialogTipsBinding>(R.layout.dialog_tips, dialog) {

    open val bulletCharacter: Char = '-'
    open val tipsList: List<Int> = listOf(R.string.tips_dialog_placeholder_1)

    override val dialogFaceButtons = dialogController?.let {
        createTipsDialogButtons(source, it)
    } ?: mutableListOf()

    override fun onActivated() {
        super.onActivated()
        binding?.tipsText?.apply {
            text = tipsList.map { "$bulletCharacter ${context.getString(it)}" }
                .toList()
                .joinToString(separator = "\n")
        }
    }
}
