package com.revolution.robotics.features.configure

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.revolution.robotics.BaseFragment
import com.revolution.robotics.R
import com.revolution.robotics.core.utils.dynamicPermissions.BluetoothConnectionFlowHelper
import com.revolution.robotics.databinding.FragmentConfigureBinding
import com.revolution.robotics.features.configure.connections.ConfigureConnectionsFragment
import com.revolution.robotics.features.configure.controllers.ConfigureControllersFragment
import org.kodein.di.erased.instance

@Suppress("UnnecessaryApply")
class ConfigureFragment : BaseFragment<FragmentConfigureBinding, ConfigureViewModel>(R.layout.fragment_configure),
    ConfigureMvp.View, BluetoothConnectionFlowHelper.Listener {

    override val viewModelClass: Class<ConfigureViewModel> = ConfigureViewModel::class.java
    private val presenter: ConfigureMvp.Presenter by kodein.instance()
    private val connectionFlowHelper = BluetoothConnectionFlowHelper(kodein)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        presenter.register(this, viewModel)
        connectionFlowHelper.init(fragmentManager, this)
        binding?.toolbarViewModel = ConfigureToolbarViewModel(presenter)
    }

    override fun hideDrawer() {
        binding?.drawerConfiguration?.closeDrawers()
    }

    override fun onDestroyView() {
        presenter.unregister()
        connectionFlowHelper.shutdown()
        super.onDestroyView()
    }

    override fun startConnectionFlow() {
        connectionFlowHelper.startConnectionFlow(requireActivity())
    }

    override fun onBluetoothConnected() {
        viewModel?.isBluetoothConnected?.set(true)
    }

    override fun showConnectionsScreen() {
        commitFragmentToFrame(ConfigureConnectionsFragment.newInstance(1))
    }

    override fun showControllerScreen() {
        commitFragmentToFrame(ConfigureControllersFragment())
    }

    private fun commitFragmentToFrame(fragment: Fragment) {
        fragmentManager?.beginTransaction()?.apply {
            replace(R.id.configureFragmentFrame, fragment).commit()
        }
    }
}
