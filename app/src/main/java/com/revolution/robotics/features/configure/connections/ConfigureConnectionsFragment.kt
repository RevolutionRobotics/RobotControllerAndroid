package com.revolution.robotics.features.configure.connections

import android.os.Bundle
import android.view.View
import com.revolution.robotics.BaseFragment
import com.revolution.robotics.R
import com.revolution.robotics.core.extensions.withArguments
import com.revolution.robotics.core.utils.BundleArgumentDelegate
import com.revolution.robotics.databinding.FragmentConfigureConnectionsBinding
import org.kodein.di.erased.instance

class ConfigureConnectionsFragment :
    BaseFragment<FragmentConfigureConnectionsBinding, ConfigureConnectionsViewModel>(
        R.layout.fragment_configure_connections
    ), ConfigureConnectionsMvp.View {

    companion object {

        fun newInstance() = ConfigureConnectionsFragment()
        private var Bundle.robotId by BundleArgumentDelegate.Int("robotId")

        fun newInstance(robotId: Int) = ConfigureConnectionsFragment().withArguments { bundle ->
            bundle.robotId = robotId
        }
    }

    override val viewModelClass: Class<ConfigureConnectionsViewModel> = ConfigureConnectionsViewModel::class.java
    private val presenter: ConfigureConnectionsMvp.Presenter by kodein.instance()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presenter.register(this, viewModel)
        arguments?.robotId?.let { presenter.loadConfiguration(it) }
    }

    override fun onDestroyView() {
        presenter.unregister()
        super.onDestroyView()
    }

    fun updateConfiguration(userConfigurationId: Int) {
        presenter.loadConfiguration(userConfigurationId)
    }

    fun clearSelection() {
        presenter.clearSelection()
    }
}
