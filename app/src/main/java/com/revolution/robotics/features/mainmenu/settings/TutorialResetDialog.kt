package com.revolution.robotics.features.mainmenu.settings

import com.revolution.robotics.views.dialogs.DialogButton
import com.revolution.robotics.views.dialogs.DialogFace
import com.revolution.robotics.views.dialogs.RoboticsDialog

class TutorialResetDialog : RoboticsDialog() {
    override val hasCloseButton: Boolean = false
    override val dialogButtons: List<DialogButton> = emptyList()
    override val dialogFaces: List<DialogFace<*>> = listOf(TutorialResetDialogFace(this))
}
