package com.revolution.robotics.features.mainmenu.settings

import com.revolution.robotics.core.utils.AppPrefs
import com.revolution.robotics.core.utils.Navigator

class SettingsPresenter(private val navigator: Navigator, private val appPrefs: AppPrefs) : SettingsMvp.Presenter {
    override var view: SettingsMvp.View? = null
    override var model: SettingsViewModel? = null

    override fun navigateToResetTutorial() {
        appPrefs.showTutorial = true
        view?.showTutorialResetSuccessDialog()
    }

    override fun navigateToFirmwareUpdate() = navigator.navigate(SettingsFragmentDirections.toFirmware())

    override fun navigateToAboutApplication() = navigator.navigate(SettingsFragmentDirections.toAbout())
}
