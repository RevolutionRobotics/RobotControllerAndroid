package com.revolution.robotics.features.mainmenu.settings.about

import android.content.ActivityNotFoundException
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import com.revolution.robotics.BaseFragment
import com.revolution.robotics.R
import com.revolution.robotics.core.kodein.utils.ResourceResolver
import com.revolution.robotics.databinding.FragmentAboutBinding
import org.kodein.di.erased.instance

class AboutFragment :
    BaseFragment<FragmentAboutBinding, AboutViewModel>(R.layout.fragment_about),
    AboutMvp.View {

    override val viewModelClass: Class<AboutViewModel> = AboutViewModel::class.java

    private val presenter: AboutMvp.Presenter by kodein.instance()
    private val resourceResolver: ResourceResolver by kodein.instance()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presenter.register(this, viewModel)
        binding?.toolbarViewModel = AboutToolbarViewModel(resourceResolver)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        presenter.unregister()
    }

    override fun openIntent(intent: Intent) {
        try {
            requireActivity().startActivity(intent)
        } catch (exception: ActivityNotFoundException) {
            // TODO Error handling
            Log.d("Activity not found", exception.message)
        }
    }
}
