package com.revolution.robotics.features.mainmenu.settings.firmware.update

import android.os.Bundle
import android.view.View
import android.view.WindowManager
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
            FirmwareUpdateFailedDialogFace(this)
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

    override fun activateInfoFace(button: DialogButton) {
        (dialogFaces[0] as FirmwareUpdateInfoFace).apply {
            dialogFaceButtons.clear()
            dialogFaceButtons.add(button)
        }
        activateFace(dialogFaces.first { it is FirmwareUpdateInfoFace })
    }

    override fun setInfoViewModel(viewModel: FirmwareUpdateInfoViewModel) {
        (dialogFaces[0] as FirmwareUpdateInfoFace).setViewModel(viewModel)
    }

    override fun activateLoadingFace() {
        activateFace(dialogFaces.first { it is FirmwareUpdateLoadingDialogFace })
    }

    override fun activateSuccessFace() {
        activateFace(dialogFaces.first { it is FirmwareUpdateSuccessDialogFace })
    }

    override fun activateErrorFace() {
        activateFace(dialogFaces.first { it is FirmwareUpdateFailedDialogFace })
    }

    override fun closeDialog() {
        dismissAllowingStateLoss()
    }

    fun retryFirmwareUpload() {
        presenter.retryFirmwareUpdate()
    }
}
