package com.revolution.robotics.features.mainmenu.settings.firmware.update

import android.os.Bundle
import android.view.View
import android.view.WindowManager
import android.widget.Toast
import com.revolution.robotics.R
import com.revolution.robotics.views.dialogs.DialogButton
import com.revolution.robotics.views.dialogs.DialogFace
import com.revolution.robotics.views.dialogs.RoboticsDialog
import org.kodein.di.erased.instance

class FirmwareUpdateDialog : RoboticsDialog(), FirmwareUpdateMvp.View {
    override val hasCloseButton: Boolean = true

    override val dialogFaces: List<DialogFace<*>> =
        listOf(
            FirmwareUpdateInfoFace(this),
            FirmwareUpdateLoadingDialogFace(this),
            FirmwareUpdateSuccessDialogFace(this),
            FirmwareUpdateFailedDialogFace(this),
            FirmwareUpdateConfirmationDialogFace(this),
            ChangeRobotBrainNameFace(this)
        )
    override val dialogButtons: List<DialogButton> = listOf()

    private val presenter: FirmwareUpdateMvp.Presenter by kodein.instance()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // The faces have only viewModels
        activity?.window?.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
        presenter.register(this, null)
    }

    override fun onDestroyView() {
        presenter.unregister()
        activity?.window?.clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
        super.onDestroyView()
    }

    override fun activateInfoFace(button: DialogButton?) {
        if (button != null) {
            (dialogFaces[0] as FirmwareUpdateInfoFace).apply {
                dialogFaceButtons[0] = button
            }
        }
        activateFace(dialogFaces.first { it is FirmwareUpdateInfoFace })
    }

    override fun onDialogCloseButtonClicked(): Boolean {
        presenter.onCloseClicked()
        return false
    }

    override fun activateConfirmationFace() {
        activateFace(dialogFaces.first { it is FirmwareUpdateConfirmationDialogFace })
    }

    override fun activateRenameBrainFace() {
        activateFace(dialogFaces.first { it is ChangeRobotBrainNameFace })
    }

    override fun showRenameError() {
        Toast.makeText(context, R.string.firmware_change_robot_name_failed, Toast.LENGTH_SHORT).show()
    }

    fun stopFrameworkUpdate() {
        presenter.stopFirmwareUpdate()
        dismissAllowingStateLoss()
    }

    override fun setInfoViewModel(viewModel: FirmwareUpdateInfoViewModel) {
        (dialogFaces.first { it is FirmwareUpdateInfoFace } as FirmwareUpdateInfoFace).setViewModel(viewModel)
        (dialogFaces.first { it is ChangeRobotBrainNameFace } as ChangeRobotBrainNameFace).setViewModel(viewModel)
    }

    override fun activateLoadingFace() {
        changeCancelableState(false)
        activateFace(dialogFaces.first { it is FirmwareUpdateLoadingDialogFace })
    }

    override fun activateSuccessFace() {
        changeCancelableState(true)
        activateFace(dialogFaces.first { it is FirmwareUpdateSuccessDialogFace })
    }

    override fun activateErrorFace() {
        changeCancelableState(true)
        activateFace(dialogFaces.first { it is FirmwareUpdateFailedDialogFace })
    }

    override fun closeDialog() {
        dismissAllowingStateLoss()
    }

    fun retryFirmwareUpload() {
        presenter.retryFirmwareUpdate()
    }

    private fun changeCancelableState(isCancelable: Boolean) {
        dialog?.setCancelable(isCancelable)
        dialog?.setCanceledOnTouchOutside(isCancelable)
    }
}
