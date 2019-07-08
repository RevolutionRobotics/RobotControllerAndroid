package com.revolution.robotics.features.configure.robotPicture

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import com.revolution.robotics.R
import com.revolution.robotics.core.eventBus.dialog.DialogEvent
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
        private var Bundle.defaultCoverImage by BundleArgumentDelegate.StringNullable("defaultCoverImage")

        fun newInstance(id: Int, defaultCoverImage: String? = null) =
            RobotPictureDialog().withArguments { bundle ->
                bundle.robotId = id
                bundle.defaultCoverImage = defaultCoverImage
            }
    }

    private val dialogFace = RobotPictureDialogFace()
    private lateinit var cameraHelper: CameraHelper
    private var defaultCoverImage: String? = null

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
        defaultCoverImage = arguments?.defaultCoverImage
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
            binding?.viewModel = RobotPictureViewModel().apply {
                defaultCoverImage.set(this@RobotPictureDialog.defaultCoverImage)
            }
            onCameraCaptured(defaultCoverImage == null)
        }

        fun onImageDeleted() {
            dialogEventBus.publish(DialogEvent.ROBOT_IMAGE_CHANGED)
            binding?.viewModel?.image?.set(null)
        }

        fun onCameraCaptured(openCameraIfFileDoesNotExist: Boolean) {
            val imageFile = cameraHelper.getImageFile(requireContext())
            dialogEventBus.publish(DialogEvent.ROBOT_IMAGE_CHANGED)
            if (imageFile.exists()) {
                binding?.viewModel?.image?.set(imageFile)
            } else if (openCameraIfFileDoesNotExist) {
                startCamera()
            }
        }
    }
}
