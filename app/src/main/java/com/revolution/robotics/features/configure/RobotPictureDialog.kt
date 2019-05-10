package com.revolution.robotics.features.configure

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import com.revolution.robotics.R
import com.revolution.robotics.core.extensions.withArguments
import com.revolution.robotics.core.utils.BundleArgumentDelegate
import com.revolution.robotics.core.utils.CameraHelper
import com.revolution.robotics.databinding.DialogRobotPictureBinding
import com.revolution.robotics.views.dialogs.DialogButton
import com.revolution.robotics.views.dialogs.DialogFace
import com.revolution.robotics.views.dialogs.RoboticsDialog

class RobotPictureDialog : RoboticsDialog() {

    companion object {
        private const val REQUEST_CODE_CAMERA = 10001

        private var Bundle.robotId by BundleArgumentDelegate.Int("robotId")

        fun newInstance(id: Int) = RobotPictureDialog().withArguments { bundle ->
            bundle.robotId = id
        }
    }

    private val dialogFace = RobotPictureDialogFace()
    private lateinit var cameraHelper: CameraHelper

    override val hasCloseButton = true
    override val dialogFaces: List<DialogFace<*>> = listOf(dialogFace)
    override val dialogButtons = listOf(
        DialogButton(R.string.camera_dialog_delete_title, R.drawable.ic_delete) {
            cameraHelper.getImageFile(requireContext()).delete()
            dialogFace.onImageDeleted()
        },
        DialogButton(R.string.camera_dialog_new_photo_title, R.drawable.ic_camera, true) {
            startCamera()
        }
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        cameraHelper = CameraHelper(arguments?.robotId ?: 0)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == REQUEST_CODE_CAMERA && resultCode == Activity.RESULT_OK) {
            dialogFace.onCameraCaptured(false)
        } else {
            super.onActivityResult(requestCode, resultCode, data)
        }
    }

    private fun startCamera() {
        cameraHelper.startCameraActivity(this, REQUEST_CODE_CAMERA)
    }

    inner class RobotPictureDialogFace : DialogFace<DialogRobotPictureBinding>(R.layout.dialog_robot_picture) {

        override fun onActivated() {
            onCameraCaptured(true)
        }

        fun onImageDeleted() {
            binding?.apply {
                image = null
                executePendingBindings()
            }
        }

        fun onCameraCaptured(openCameraIfImageDoesNotExist: Boolean) {
            val imageFile = cameraHelper.getImageFile(requireContext())
            if (imageFile.exists()) {
                binding?.apply {
                    image = imageFile
                    executePendingBindings()
                }
            } else if (openCameraIfImageDoesNotExist) {
                startCamera()
            }
        }
    }
}
