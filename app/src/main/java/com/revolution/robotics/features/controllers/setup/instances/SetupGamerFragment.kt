package com.revolution.robotics.features.controllers.setup.instances

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.revolution.robotics.core.utils.Navigator
import com.revolution.robotics.databinding.FragmentControllerSetupGamerBinding
import com.revolution.robotics.features.controllers.setup.SetupFragment
import com.revolution.robotics.features.controllers.setup.SetupViewModel
import org.kodein.di.erased.instance

class SetupGamerFragment : SetupFragment() {

    private lateinit var contentBinding: FragmentControllerSetupGamerBinding
    private val navigator: Navigator by kodein.instance()

    override fun createContentView(inflater: LayoutInflater, container: ViewGroup?): View {
        contentBinding = FragmentControllerSetupGamerBinding.inflate(inflater, container, true).apply {
            viewModel = this@SetupGamerFragment.viewModel
        }
        return contentBinding.root
    }

    override fun updateBinding(viewModel: SetupViewModel) {
        contentBinding.apply {
            this.viewModel = viewModel
            executePendingBindings()
        }
    }

    override fun onShowAllProgramsSelected() {
        navigator.navigate(SetupGamerFragmentDirections.toProgramSelectorFragment())
    }
}
