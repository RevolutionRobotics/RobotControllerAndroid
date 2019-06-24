package com.revolution.robotics.features.mainmenu.settings.about

import android.content.Intent
import com.revolution.robotics.core.Mvp

interface AboutMvp : Mvp {

    interface View : Mvp.View {
        fun openIntent(intent: Intent)
    }

    interface Presenter : Mvp.Presenter<View, AboutViewModel> {
        fun onFacebookClicked()
        fun onInstagramClicked()
        fun onWebsiteClicked()
        fun onPermissionLayoutClicked()
        fun onPrivacyPolicyClicked()
        fun onTermsAndConditionsClicked()
    }
}
