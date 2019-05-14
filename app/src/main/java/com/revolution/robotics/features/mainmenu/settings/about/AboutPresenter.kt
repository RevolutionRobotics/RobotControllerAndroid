package com.revolution.robotics.features.mainmenu.settings.about

import android.content.Intent
import android.net.Uri
import android.provider.Settings
import com.revolution.robotics.BuildConfig
import com.revolution.robotics.R
import com.revolution.robotics.core.kodein.utils.ApplicationContextProvider
import com.revolution.robotics.core.kodein.utils.ResourceResolver

class AboutPresenter(
    private val resourceResolver: ResourceResolver,
    private val applicationContextProvider: ApplicationContextProvider
) : AboutMvp.Presenter {

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
        view?.openIntent(Intent(Intent.ACTION_VIEW, Uri.parse(FACEBOOK)))
    }

    override fun onInstagramClicked() {
        view?.openIntent(Intent(Intent.ACTION_VIEW, Uri.parse(INSTAGRAM)))
    }

    override fun onWebsiteClicked() {
        view?.openIntent(Intent(Intent.ACTION_VIEW, Uri.parse(WEBPAGE)))
    }

    override fun onPermissionLayoutClicked() {
        Intent().apply {
            action = Settings.ACTION_APPLICATION_DETAILS_SETTINGS
            data = Uri.fromParts("package", applicationContextProvider.applicationContext.packageName, null)
            view?.openIntent(this)
        }
    }
}
