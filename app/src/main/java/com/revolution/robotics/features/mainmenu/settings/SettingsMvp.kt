package com.revolution.robotics.features.mainmenu.settings

import com.revolution.robotics.core.Mvp

interface SettingsMvp : Mvp {
    interface View : Mvp.View

    interface Presenter : Mvp.Presenter<View, SettingsViewModel> {
        fun navigateToResetTutorial()
        fun navigateToFirmwareUpdate()
        fun navigateToAboutApplication()
    }
}
