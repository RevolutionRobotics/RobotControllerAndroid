package com.revolution.robotics.features.mainmenu.settings

import android.os.Bundle
import android.view.View
import com.revolution.robotics.BaseFragment
import com.revolution.robotics.R
import com.revolution.robotics.analytics.Reporter
import com.revolution.robotics.core.eventBus.dialog.DialogEvent
import com.revolution.robotics.core.eventBus.dialog.DialogEventBus
import com.revolution.robotics.core.kodein.utils.ResourceResolver
import com.revolution.robotics.core.utils.AppPrefs
import com.revolution.robotics.core.utils.Navigator
import com.revolution.robotics.databinding.FragmentSettingsBinding
import com.revolution.robotics.features.mainmenu.settings.changeServer.ChangeServerDialog
import org.kodein.di.erased.instance
import kotlin.system.exitProcess

class SettingsFragment : BaseFragment<FragmentSettingsBinding, SettingsViewModel>(R.layout.fragment_settings),
    SettingsMvp.View, DialogEventBus.Listener {

    override val viewModelClass: Class<SettingsViewModel> = SettingsViewModel::class.java
    override val screen = Reporter.Screen.SETTINGS

    private val resourceResolver: ResourceResolver by kodein.instance()
    private val presenter: SettingsMvp.Presenter by kodein.instance()
    private val navigator: Navigator by kodein.instance()
    private val appPrefs: AppPrefs by kodein.instance()
    private val dialogEventBus: DialogEventBus by kodein.instance()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        presenter.register(this, viewModel)
        updateServerButtonText()
        dialogEventBus.register(this)
        binding?.toolbarViewModel = SettingsToolbarViewModel(resourceResolver)
    }

    override fun showTutorialResetSuccessDialog() {
        TutorialResetDialog().show(fragmentManager)
    }

    override fun showServerSelectionPopup() {
        ChangeServerDialog().show(fragmentManager)
    }

    override fun onBackPressed(): Boolean {
        navigator.back()
        return true
    }

    override fun onDestroyView() {
        presenter.unregister()
        dialogEventBus.unregister(this)
        super.onDestroyView()
    }

    override fun onDialogEvent(event: DialogEvent) {
        when (event) {
            DialogEvent.SERVER_LOCATION_CHANGED -> updateServerButtonText()
            DialogEvent.QUIT_APP -> {
                activity?.finishAffinity()
                exitProcess(0);
            }
            else -> Unit
        }
    }

    private fun updateServerButtonText() {
        viewModel?.changeServerLocationButtonText?.value = resourceResolver.string(
            R.string.settings_change_server_location,
            if (appPrefs.useAsiaApi) "Mainland China" else "Global")
    }
}
