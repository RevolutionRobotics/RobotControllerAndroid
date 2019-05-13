package com.revolution.robotics.features.mainmenu.settings.about

import com.revolution.robotics.core.Mvp

interface AboutMvp : Mvp {

    interface View : Mvp.View {
        fun openUrl(url: String)
    }

    interface Presenter : Mvp.Presenter<View, AboutViewModel> {
        fun onFacebookClicked()
        fun onInstagramClicked()
        fun onWebsiteClicked()
    }
}
