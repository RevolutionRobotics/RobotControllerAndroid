package com.revolution.robotics.features.mainmenu.settings

import android.os.Bundle
import android.view.View
import com.revolution.robotics.BaseFragment
import com.revolution.robotics.R
import com.revolution.robotics.core.kodein.utils.ResourceResolver
import com.revolution.robotics.databinding.FragmentSettingsBinding
import org.kodein.di.erased.instance

class SettingsFragment : BaseFragment<FragmentSettingsBinding, SettingsViewModel>(R.layout.fragment_settings),
    SettingsMvp.View {

    override val viewModelClass: Class<SettingsViewModel> = SettingsViewModel::class.java
    private val resourceResolver: ResourceResolver by kodein.instance()
    private val presenter: SettingsMvp.Presenter by kodein.instance()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        presenter.register(this, viewModel)
        binding?.toolbarViewModel = SettingsToolbarViewModel(resourceResolver)
    }

    override fun showTutorialResetSuccessDialog() {
        TutorialResetDialog().show(fragmentManager)
    }

    override fun onDestroyView() {
        presenter.unregister()
        super.onDestroyView()
    }
}
