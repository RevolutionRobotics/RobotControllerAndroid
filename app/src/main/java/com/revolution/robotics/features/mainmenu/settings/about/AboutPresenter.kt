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

    // TODO remove this suppress when the urls are finalised
    @Suppress("StringLiteralDuplication")
    companion object {
        private const val FACEBOOK = "https://www.facebook.com/RevoRobotics"
        private const val INSTAGRAM = "https://www.instagram.com/revorobotics"
        private const val WEBPAGE = "https://www.revolutionrobotics.org/"
        // TODO update PP url
        private const val PRIVACY_POLICY = "https://www.revolutionrobotics.org/"
        // TODO update T&C url
        private const val TERMS_AND_CONDITIONS = "https://www.revolutionrobotics.org/"
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

    override fun onPermissionLayoutClicked() {
        Intent().apply {
            action = Settings.ACTION_APPLICATION_DETAILS_SETTINGS
            data = Uri.fromParts("package", applicationContextProvider.applicationContext.packageName, null)
            view?.openIntent(this)
        }
    }

    override fun onPrivacyPolicyClicked() {
        view?.openUrl(PRIVACY_POLICY)
    }

    override fun onTermsAndConditionsClicked() {
        view?.openUrl(TERMS_AND_CONDITIONS)
    }
}
