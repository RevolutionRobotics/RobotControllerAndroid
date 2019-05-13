package com.revolution.robotics.features.configure.connections

import android.os.Bundle
import android.view.View
import com.revolution.robotics.BaseFragment
import com.revolution.robotics.R
import com.revolution.robotics.core.domain.local.UserConfiguration
import com.revolution.robotics.core.extensions.withArguments
import com.revolution.robotics.core.utils.BundleArgumentDelegate
import com.revolution.robotics.databinding.FragmentConfigureConnectionsBinding
import org.kodein.di.erased.instance

class ConfigureConnectionsFragment :
    BaseFragment<FragmentConfigureConnectionsBinding, ConfigureConnectionsViewModel>(
        R.layout.fragment_configure_connections
    ), ConfigureConnectionsMvp.View {

    companion object {

        private var Bundle.userConfiguration: UserConfiguration by BundleArgumentDelegate.Parcelable("userConfig")

        fun newInstance(userConfiguration: UserConfiguration) = ConfigureConnectionsFragment().withArguments {
            it.userConfiguration = userConfiguration
        }
    }

    override val viewModelClass: Class<ConfigureConnectionsViewModel> = ConfigureConnectionsViewModel::class.java
    private val presenter: ConfigureConnectionsMvp.Presenter by kodein.instance()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presenter.register(this, viewModel)
        arguments?.let {
            presenter.setConfiguration(it.userConfiguration)
        }
    }

    override fun onDestroyView() {
        presenter.unregister()
        super.onDestroyView()
    }

    fun updateConfiguration(userConfiguration: UserConfiguration) {
        presenter.setConfiguration(userConfiguration)
    }

    fun clearSelection() {
        presenter.clearSelection()
    }
}