package com.revolution.robotics.features.mainmenu.settings.firmware.update

import androidx.lifecycle.ViewModel
import com.revolution.robotics.core.Mvp
import com.revolution.robotics.views.dialogs.DialogButton

interface FirmwareUpdateMvp : Mvp {

    interface View : Mvp.View {
        fun activateInfoFace(button: DialogButton)
        fun setInfoViewModel(viewModel: FirmwareUpdateInfoViewModel)
        fun activateLoadingFace()
        fun activateSuccessFace()
        fun activateErrorFace()
        fun closeDialog()
    }

    interface Presenter : Mvp.Presenter<View, ViewModel> {
        fun onCheckForUpdatesClicked()
        fun retryFirmwareUpdate()
        fun stopFirmwareUpdate()
    }
}
