package com.revolution.robotics.features.controllers.setup.instances

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.revolution.robotics.core.utils.Navigator
import com.revolution.robotics.databinding.FragmentControllerSetupMultitaskerBinding
import com.revolution.robotics.features.controllers.setup.SetupFragment
import org.kodein.di.erased.instance

class SetupMultitaskerFragment : SetupFragment() {

    private lateinit var contentBinding: FragmentControllerSetupMultitaskerBinding
    private val navigator: Navigator by kodein.instance()

    override fun createContentView(inflater: LayoutInflater, container: ViewGroup?): View {
        contentBinding = FragmentControllerSetupMultitaskerBinding.inflate(inflater, container, true).apply {
            viewModel = this@SetupMultitaskerFragment.viewModel
        }
        return contentBinding.root
    }

    override fun updateBinding() {
        contentBinding.apply {
            this.viewModel = viewModel
            executePendingBindings()
        }
    }

    override fun onShowAllProgramsSelected() {
        navigator.navigate(SetupMultitaskerFragmentDirections.toProgramSelectorFragment())
    }
}
