package com.revolution.robotics.features.controllers.setup.instances

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.revolution.robotics.core.utils.Navigator
import com.revolution.robotics.databinding.FragmentControllerSetupDriverBinding
import com.revolution.robotics.features.controllers.setup.SetupFragment
import org.kodein.di.erased.instance

class SetupDriverFragment : SetupFragment() {

    private lateinit var contentBinding: FragmentControllerSetupDriverBinding
    private val navigator: Navigator by kodein.instance()

    override fun createContentView(inflater: LayoutInflater, container: ViewGroup?): View {
        contentBinding = FragmentControllerSetupDriverBinding.inflate(inflater, container, true).apply {
            viewModel = this@SetupDriverFragment.viewModel
        }
        return contentBinding.root
    }

    override fun updateBinding() {
        contentBinding.apply {
            this.viewModel = viewModel
            executePendingBindings()
        }
    }

    override fun navigateToTheBackgroundPrograms() {
        navigator.navigate(SetupDriverFragmentDirections.toButtonlessProgramSelectorFragment())
    }

    override fun onShowAllProgramsSelected() {
        navigator.navigate(SetupDriverFragmentDirections.toProgramSelectorFragment())
    }
}
