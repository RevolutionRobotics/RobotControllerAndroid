package com.revolution.robotics.features.build.connect

import android.os.Bundle
import android.view.View
import com.revolution.robotics.analytics.Reporter
import com.revolution.robotics.features.build.connect.availableRobotsFace.ConnectDialogFace
import com.revolution.robotics.views.dialogs.DialogButton
import com.revolution.robotics.views.dialogs.RoboticsDialog
import org.kodein.di.erased.instance

class ConnectDialog : RoboticsDialog() {

    companion object {
        fun newInstance() = ConnectDialog()
    }

    private val reporter: Reporter by kodein.instance()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        reporter.reportEvent(Reporter.Event.OPEN_BT_DEVICE_LIST)
    }

    override val hasCloseButton = true
    override val dialogFaces = listOf(ConnectDialogFace(this))
    override val dialogButtons = emptyList<DialogButton>()
}
