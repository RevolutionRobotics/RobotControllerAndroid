package com.revolution.robotics.features.mainmenu.settings.firmware.update

import android.os.Bundle
import android.view.View
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
            FirmwareUpdateSuccessDialogFace(this)
        )
    override val dialogButtons: List<DialogButton> = listOf()

    private val presenter: FirmwareUpdateMvp.Presenter by kodein.instance()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // The faces have only viewModels
        presenter.register(this, null)
    }

    override fun onDestroyView() {
        presenter.unregister()
        super.onDestroyView()
    }

    override fun activateInfoFace(button: DialogButton) {
        (dialogFaces[0] as FirmwareUpdateInfoFace).apply {
            dialogFaceButtons.clear()
            dialogFaceButtons.add(button)
        }
        activateFace(dialogFaces[0])
    }

    override fun setInfoViewModel(viewModel: FirmwareUpdateInfoViewModel) {
        (dialogFaces[0] as FirmwareUpdateInfoFace).setViewModel(viewModel)
    }

    override fun activateLoadingFace() {
        activateFace(dialogFaces[1])
    }

    override fun activateSuccessFace() {
        activateFace(dialogFaces[2])
    }
}