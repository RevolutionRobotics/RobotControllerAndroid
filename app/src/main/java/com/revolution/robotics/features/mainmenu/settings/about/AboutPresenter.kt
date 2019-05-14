package com.revolution.robotics.features.mainmenu.settings.about

import com.revolution.robotics.BuildConfig
import com.revolution.robotics.R
import com.revolution.robotics.core.kodein.utils.ResourceResolver

class AboutPresenter(private val resourceResolver: ResourceResolver) : AboutMvp.Presenter {

    companion object {
        private const val FACEBOOK = "https://www.facebook.com/RevoRobotics"
        private const val INSTAGRAM = "https://www.instagram.com/revorobotics"
        private const val WEBPAGE = "https://www.revolutionrobotics.org/"
    }

    override var view: AboutMvp.View? = null
    override var model: AboutViewModel? = null

    override fun register(view: AboutMvp.View, model: AboutViewModel?) {
        super.register(view, model)
        model?.versionText?.value = "${resourceResolver.string(R.string.about_version)} ${BuildConfig.VERSION_NAME}"
    }

    override fun onFacebookClicked() {
        view?.openUrl(FACEBOOK)
    }

    override fun onInstagramClicked() {
        view?.openUrl(INSTAGRAM)
    }

    override fun onWebsiteClicked() {
        view?.openUrl(WEBPAGE)
    }
}

