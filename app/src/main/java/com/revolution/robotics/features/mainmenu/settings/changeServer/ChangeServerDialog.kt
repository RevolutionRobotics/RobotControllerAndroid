package com.revolution.robotics.features.mainmenu.settings.changeServer

import com.revolution.robotics.core.eventBus.dialog.DialogEvent
import com.revolution.robotics.core.utils.AppPrefs
import com.revolution.robotics.views.dialogs.DialogButton
import com.revolution.robotics.views.dialogs.DialogFace
import com.revolution.robotics.views.dialogs.RoboticsDialog
import org.kodein.di.erased.instance

class ChangeServerDialog : RoboticsDialog() {

    private val appPrefs: AppPrefs by kodein.instance()

    override val hasCloseButton: Boolean = false
    override val dialogButtons: List<DialogButton> = emptyList()
    override val dialogFaces: List<DialogFace<*>> = listOf(
        ChangeServerDialogFace(this),
        RestartAppDialogFace(this)
    )

    fun selectServer(useAsian: Boolean) {
        appPrefs.useAsiaApi = useAsian
        dialogEventBus.publish(DialogEvent.SERVER_LOCATION_CHANGED)
        activateFace(dialogFaces.first { f -> f is RestartAppDialogFace })
    }
}
