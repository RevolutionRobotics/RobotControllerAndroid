package com.revolution.robotics.features.controllers.setup.instances

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.revolution.robotics.core.domain.local.UserProgram
import com.revolution.robotics.core.utils.Navigator
import com.revolution.robotics.databinding.FragmentControllerSetupGamerBinding
import com.revolution.robotics.features.controllers.setup.SetupFragment
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

    override fun getContentBinding() = contentBinding

    override fun onShowAllProgramsSelected() {
        navigator.navigate(SetupGamerFragmentDirections.toProgramSelector())
    }

    override fun navigateToBackgroundPrograms() {
        navigator.navigate(SetupGamerFragmentDirections.toButtonlessProgramSelector())
    }

    override fun navigateToEditProgram(userProgram: UserProgram?) {
        userProgram?.let { navigator.navigate(SetupGamerFragmentDirections.toCoding(it)) }
    }
}
