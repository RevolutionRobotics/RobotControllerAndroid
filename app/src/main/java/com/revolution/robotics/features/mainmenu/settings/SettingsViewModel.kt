package com.revolution.robotics.features.mainmenu.settings

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.revolution.robotics.R
import com.revolution.robotics.views.chippedBox.ChippedBoxConfig

class SettingsViewModel(private val presenter: SettingsMvp.Presenter) : ViewModel() {
    companion object {
        val backgroundBuilder: ChippedBoxConfig.Builder = ChippedBoxConfig.Builder()
            .borderColorResource(R.color.white)
            .backgroundColorResource(R.color.grey_1d)
            .borderSize(R.dimen.dimen_1dp)
            .chipSize(R.dimen.dimen_10dp)
            .chipTopLeft(false)
            .chipTopRight(true)
            .chipBottomLeft(true)
            .chipBottomRight(false)
    }

    val buttonBackground = backgroundBuilder.create()
    var changeServerLocationButtonText: MutableLiveData<String> = MutableLiveData()

    fun onResetTutorialClick() = presenter.navigateToResetTutorial()
    fun onFirmwareUpdateClick() = presenter.navigateToFirmwareUpdate()
    fun onSelectServerClicked() = presenter.showServerSelectionPopup()
    fun onAboutApplicationClick() = presenter.navigateToAboutApplication()
}
