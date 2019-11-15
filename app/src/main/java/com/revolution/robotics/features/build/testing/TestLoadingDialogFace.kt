package com.revolution.robotics.features.build.testing

import com.revolution.robotics.R
import com.revolution.robotics.databinding.DialogFaceFirmwareUpdateLoadingBinding
import com.revolution.robotics.views.dialogs.DialogFace
import com.revolution.robotics.views.dialogs.RoboticsDialog

class TestLoadingDialogFace(dialog: RoboticsDialog) :
    DialogFace<DialogFaceFirmwareUpdateLoadingBinding>(R.layout.dialog_face_test_uploading, dialog)
