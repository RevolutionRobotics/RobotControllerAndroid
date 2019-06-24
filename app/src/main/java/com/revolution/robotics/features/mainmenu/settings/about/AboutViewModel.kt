package com.revolution.robotics.features.mainmenu.settings.about

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.revolution.robotics.R
import com.revolution.robotics.views.chippedBox.ChippedBoxConfig

class AboutViewModel(private val presenter: AboutMvp.Presenter) : ViewModel() {

    val versionText = MutableLiveData<String>()
    val permissionBackground = ChippedBoxConfig.Builder()
        .backgroundColorResource(R.color.black)
        .borderColorResource(R.color.grey_28)
        .chipSize(R.dimen.dimen_8dp)
        .chipBottomLeft(true)
        .chipTopRight(true)
        .chipBottomRight(false)
        .chipTopLeft(false)
        .borderSize(R.dimen.dimen_1dp)
        .create()

    val leftButtonBackground = ChippedBoxConfig.Builder()
        .backgroundColorResource(R.color.grey_1d)
        .chipSize(R.dimen.dimen_8dp)
        .borderColorResource(R.color.white)
        .chipBottomLeft(true)
        .chipTopRight(false)
        .chipBottomRight(false)
        .chipTopLeft(false)
        .borderSize(R.dimen.dimen_1dp)
        .create()

    val rightButtonBackground = ChippedBoxConfig.Builder()
        .backgroundColorResource(R.color.grey_1d)
        .borderColorResource(R.color.white)
        .chipSize(R.dimen.dimen_8dp)
        .chipBottomLeft(false)
        .chipTopRight(true)
        .chipBottomRight(false)
        .chipTopLeft(false)
        .borderSize(R.dimen.dimen_1dp)
        .create()

    fun onFacebookClicked() {
        presenter.onFacebookClicked()
    }

    fun onWebsiteClicked() {
        presenter.onWebsiteClicked()
    }

    fun onInstagramClicked() {
        presenter.onInstagramClicked()
    }

    fun onPermissionLayoutClicked() {
        presenter.onPermissionLayoutClicked()
    }

    fun onPrivacyPolicyClicked() {
        presenter.onPrivacyPolicyClicked()
    }

    fun onTermsAndConditionClicked() {
        presenter.onTermsAndConditionsClicked()
    }
}
