package com.revolution.robotics.features.myRobots.info

import android.os.Bundle
import com.revolution.robotics.core.domain.local.UserRobot
import com.revolution.robotics.core.extensions.withArguments
import com.revolution.robotics.core.interactor.GetAllUserRobotsInteractor
import com.revolution.robotics.core.interactor.firebase.RobotsInteractor
import com.revolution.robotics.core.utils.BundleArgumentDelegate
import com.revolution.robotics.views.dialogs.DialogButton
import com.revolution.robotics.views.dialogs.DialogFace
import com.revolution.robotics.views.dialogs.RoboticsDialog
import org.kodein.di.erased.instance

sealed class InfoRobotDialog(openBuildFlow: Boolean) : RoboticsDialog() {

    companion object {
        const val KEY_ROBOT = "userRobot"

        var Bundle.userRobot: UserRobot by BundleArgumentDelegate.Parcelable(KEY_ROBOT)
    }

    override val hasCloseButton = false
    override val dialogFaces: List<DialogFace<*>> = listOf(
        InfoRobotDialogFace(this, openBuildFlow),
        DeleteRobotDialogFace(this),
        CannotDeleteLastRobotDialogFace(this)
    )
    override val dialogButtons = emptyList<DialogButton>()

    private val getAllUserRobotsInteractor: GetAllUserRobotsInteractor by kodein.instance()

    fun activateDeleteFace() {
        getAllUserRobotsInteractor.execute {
            if (it.count() > 1) {
                activateFace(dialogFaces.first { it is DeleteRobotDialogFace })
            } else {
                activateFace(dialogFaces.first { it is CannotDeleteLastRobotDialogFace })
            }
        }
    }

    class InProgress : InfoRobotDialog(true) {
        companion object {
            fun newInstance(userRobot: UserRobot) = InProgress().withArguments {
                it.userRobot = userRobot
            }
        }
    }

    class Edit : InfoRobotDialog(false) {
        companion object {
            fun newInstance(userRobot: UserRobot) = Edit().withArguments {
                it.userRobot = userRobot
            }
        }
    }
}
