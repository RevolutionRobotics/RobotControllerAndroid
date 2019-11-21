package com.revolution.robotics.features.mainmenu.settings

import com.revolution.robotics.core.utils.AppPrefs
import com.revolution.robotics.core.utils.Navigator
import com.revolution.robotics.core.utils.PreferenceFieldDelegate

class SettingsPresenter(private val navigator: Navigator, private val appPrefs: AppPrefs) : SettingsMvp.Presenter {
    override var view: SettingsMvp.View? = null
    override var model: SettingsViewModel? = null

    override fun navigateToResetTutorial() {
        appPrefs.userTypeSelected = false
        appPrefs.onboardingRobotBuild = false
        appPrefs.onboardingRobotDriven = false
        appPrefs.finishedOnboarding = false
        view?.showTutorialResetSuccessDialog()
    }

    override fun navigateToFirmwareUpdate() = navigator.navigate(SettingsFragmentDirections.toFirmware())

    override fun navigateToAboutApplication() = navigator.navigate(SettingsFragmentDirections.toAbout())
}
