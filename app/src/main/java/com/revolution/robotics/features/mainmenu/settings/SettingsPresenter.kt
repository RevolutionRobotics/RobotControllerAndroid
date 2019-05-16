package com.revolution.robotics.features.mainmenu.settings

import com.revolution.robotics.core.utils.Navigator

class SettingsPresenter(private val navigator: Navigator) : SettingsMvp.Presenter {
    override var view: SettingsMvp.View? = null
    override var model: SettingsViewModel? = null

    override fun navigateToResetTutorial() {
        // TODO reset tutorial
        view?.showTutorialResetSuccessDialog()
    }

    override fun navigateToFirmwareUpdate() = navigator.navigate(SettingsFragmentDirections.toFirmwareFragment())

    override fun navigateToAboutApplication() = navigator.navigate(SettingsFragmentDirections.toAboutFragment())
}
