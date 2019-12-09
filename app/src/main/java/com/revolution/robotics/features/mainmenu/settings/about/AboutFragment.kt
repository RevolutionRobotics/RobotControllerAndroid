package com.revolution.robotics.features.mainmenu.settings.about

import android.content.ActivityNotFoundException
import android.content.Intent
import android.os.Bundle
import android.view.View
import com.revolution.robotics.BaseFragment
import com.revolution.robotics.R
import com.revolution.robotics.analytics.Reporter
import com.revolution.robotics.core.extensions.openUrl
import com.revolution.robotics.core.kodein.utils.ResourceResolver
import com.revolution.robotics.databinding.FragmentAboutBinding
import com.revolution.robotics.features.shared.ErrorHandler
import org.kodein.di.erased.instance

class AboutFragment :
    BaseFragment<FragmentAboutBinding, AboutViewModel>(R.layout.fragment_about),
    AboutMvp.View {

    override val viewModelClass: Class<AboutViewModel> = AboutViewModel::class.java
    override val screen = Reporter.Screen.ABOUT

    private val presenter: AboutMvp.Presenter by kodein.instance()
    private val resourceResolver: ResourceResolver by kodein.instance()
    private val errorHandler: ErrorHandler by kodein.instance()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presenter.register(this, viewModel)
        binding?.toolbarViewModel = AboutToolbarViewModel(resourceResolver)
    }

    override fun onDestroyView() {
        presenter.unregister()
        super.onDestroyView()
    }

    @Suppress("SwallowedException")
    override fun openIntent(intent: Intent) {
        try {
            requireActivity().startActivity(intent)
        } catch (exception: ActivityNotFoundException) {
            errorHandler.onError(exception, R.string.error_cannot_open_url)
        }
    }

    override fun openUrl(url: String) {
        requireActivity().openUrl(url, errorHandler)
    }
}
