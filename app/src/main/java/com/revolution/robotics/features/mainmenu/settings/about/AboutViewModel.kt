package com.revolution.robotics.features.mainmenu.settings.about

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class AboutViewModel(private val presenter: AboutMvp.Presenter) : ViewModel() {

    val versionText = MutableLiveData<String>()

    fun onFacebookClicked() {

    }

    fun onWebsiteClicked() {

    }

    fun onInstagramClicked() {

    }

    fun onPrivacyPolicyClicked() {
        // TODO Open privacy policy
    }

    fun onTermsAndConditionClicked() {
        // TODO Open terms
    }
}