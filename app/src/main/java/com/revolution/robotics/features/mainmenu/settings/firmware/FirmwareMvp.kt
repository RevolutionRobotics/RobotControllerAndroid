package com.revolution.robotics.features.mainmenu.settings.firmware

import com.revolution.robotics.core.Mvp

interface FirmwareMvp : Mvp {

    interface View : Mvp.View

    interface Presenter : Mvp.Presenter<View, FirmwareUpdateViewModel>
}