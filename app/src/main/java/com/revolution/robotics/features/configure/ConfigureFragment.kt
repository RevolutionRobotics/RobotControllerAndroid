package com.revolution.robotics.features.configure

import android.os.Bundle
import android.view.View
import com.revolution.robotics.BaseFragment
import com.revolution.robotics.R
import com.revolution.robotics.databinding.FragmentConfigureBinding
import org.kodein.di.erased.instance
import androidx.fragment.app.Fragment
import com.revolution.robotics.core.domain.remote.Motor
import com.revolution.robotics.core.domain.remote.Sensor
import com.revolution.robotics.core.utils.dynamicPermissions.BluetoothConnectionFlowHelper
import com.revolution.robotics.features.configure.connections.ConfigureConnectionsFragment

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
        commitFragmentToFrame(ConfigureConnectionsFragment())
    }

    override fun showControllerScreen() {
        // TODO show controller screen here
    }

    private fun commitFragmentToFrame(fragment: Fragment) {
        fragmentManager?.beginTransaction()?.apply {
            replace(R.id.configureFragmentFrame, fragment).commit()
        }
    }
}
