package com.revolution.robotics.features.configure.delete

import android.os.Bundle
import com.revolution.robotics.core.domain.local.UserRobot
import com.revolution.robotics.core.extensions.withArguments
import com.revolution.robotics.core.utils.BundleArgumentDelegate
import com.revolution.robotics.features.myRobots.info.DeleteRobotDialogFace
import com.revolution.robotics.views.dialogs.DialogButton
import com.revolution.robotics.views.dialogs.DialogFace
import com.revolution.robotics.views.dialogs.RoboticsDialog

class DeleteRobotDialog : RoboticsDialog() {
    companion object {
        const val KEY_ROBOT = "userRobot"

        var Bundle.userRobot: UserRobot by BundleArgumentDelegate.Parcelable(KEY_ROBOT)

        fun newInstance(userRobot: UserRobot) = DeleteRobotDialog().withArguments {
            it.userRobot = userRobot
        }
    }

    override val hasCloseButton = false
    override val dialogFaces: List<DialogFace<*>> = listOf(
        DeleteRobotDialogFace(this)
    )
    override val dialogButtons = emptyList<DialogButton>()

}