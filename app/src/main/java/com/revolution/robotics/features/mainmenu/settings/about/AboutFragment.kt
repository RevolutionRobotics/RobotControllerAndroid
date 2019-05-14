package com.revolution.robotics.features.mainmenu.settings.about

import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import com.revolution.robotics.BaseFragment
import com.revolution.robotics.R
import com.revolution.robotics.databinding.FragmentAboutBinding
import org.kodein.di.erased.instance

class AboutFragment :
    BaseFragment<FragmentAboutBinding, AboutViewModel>(R.layout.fragment_about),
    AboutMvp.View {

    override val viewModelClass: Class<AboutViewModel> = AboutViewModel::class.java

    private val presenter: AboutMvp.Presenter by kodein.instance()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presenter.register(this, viewModel)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        presenter.unregister()
    }

    override fun openUrl(url: String) {
        try {
            requireActivity().startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(url)))
        } catch (exception: ActivityNotFoundException) {
            // TODO Error handling
        }
    }
}