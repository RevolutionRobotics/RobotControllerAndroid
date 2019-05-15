package com.revolution.robotics.features.mainmenu.settings.firmware

import com.revolution.robotics.core.Mvp

interface FirmwareMvp : Mvp {

    interface View : Mvp.View {
        fun showFirmwareUpdateDialog()
    }

    interface Presenter : Mvp.Presenter<View, FirmwareUpdateViewModel> {
        fun onConnectClicked()
        fun onRobotClicked()
    }
}
