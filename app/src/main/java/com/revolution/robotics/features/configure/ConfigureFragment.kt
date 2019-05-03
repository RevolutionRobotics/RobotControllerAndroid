package com.revolution.robotics.features.configure

import android.os.Bundle
import android.text.TextUtils.replace
import android.view.View
import com.revolution.robotics.BaseFragment
import com.revolution.robotics.R
import com.revolution.robotics.databinding.FragmentConfigureBinding
import org.kodein.di.erased.instance
import androidx.fragment.app.Fragment
import com.revolution.robotics.features.configure.connections.ConfigureConnectionsFragment

@Suppress("UnnecessaryApply")
class ConfigureFragment : BaseFragment<FragmentConfigureBinding, ConfigureViewModel>(R.layout.fragment_configure),
    ConfigureMvp.View {

    override val viewModelClass: Class<ConfigureViewModel> = ConfigureViewModel::class.java
    private val presenter: ConfigureMvp.Presenter by kodein.instance()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        presenter.register(this, viewModel)
        binding?.toolbarViewModel = ConfigureToolbarViewModel()
    }

    override fun showConnectionsScreen() {
        commitFragmentToFrame(ConfigureConnectionsFragment())
    }

    override fun showControllerScreen() {}

    private fun commitFragmentToFrame(fragment: Fragment) {
        fragmentManager?.beginTransaction()?.apply {
            replace(R.id.configureFragmentFrame, fragment).commit()
        }
    }

    override fun onDestroyView() {
        presenter.unregister()
        super.onDestroyView()
    }
}
