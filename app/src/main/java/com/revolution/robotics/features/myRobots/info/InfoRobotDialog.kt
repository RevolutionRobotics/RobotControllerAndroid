package com.revolution.robotics.features.myRobots.info

import android.os.Bundle
import com.revolution.robotics.core.domain.local.UserRobot
import com.revolution.robotics.core.extensions.withArguments
import com.revolution.robotics.core.utils.BundleArgumentDelegate
import com.revolution.robotics.views.dialogs.DialogButton
import com.revolution.robotics.views.dialogs.DialogFace
import com.revolution.robotics.views.dialogs.RoboticsDialog

sealed class InfoRobotDialog(showEditButton: Boolean) : RoboticsDialog() {

    companion object {
        const val KEY_ROBOT = "userRobot"

        var Bundle.userRobot: UserRobot by BundleArgumentDelegate.Parcelable(KEY_ROBOT)
    }

    override val hasCloseButton = false
    override val dialogFaces: List<DialogFace<*>> = listOf(
        InfoRobotDialogFace(this, showEditButton),
        DeleteRobotDialogFace(this)
    )
    override val dialogButtons = emptyList<DialogButton>()

    fun activateDeleteFace() {
        activateFace(dialogFaces.first { it is DeleteRobotDialogFace })
    }

    class Normal : InfoRobotDialog(false) {
        companion object {
            fun newInstance(userRobot: UserRobot) = Normal().withArguments {
                it.userRobot = userRobot
            }
        }
    }

    class Edit : InfoRobotDialog(true) {
        companion object {
            fun newInstance(userRobot: UserRobot) = Edit().withArguments {
                it.userRobot = userRobot
            }
        }
    }
}
