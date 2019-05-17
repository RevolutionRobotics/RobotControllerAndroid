package com.revolution.robotics.features.controllers.setup.instances

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.revolution.robotics.databinding.FragmentControllerSetupMultitaskerBinding
import com.revolution.robotics.features.controllers.setup.SetupFragment
import com.revolution.robotics.features.controllers.setup.SetupViewModel

class SetupMultitaskerFragment : SetupFragment() {

    private lateinit var contentBinding: FragmentControllerSetupMultitaskerBinding

    override fun createContentView(inflater: LayoutInflater, container: ViewGroup?): View {
        contentBinding = FragmentControllerSetupMultitaskerBinding.inflate(inflater, container, true).apply {
            viewModel = this@SetupMultitaskerFragment.viewModel
        }
        return contentBinding.root
    }

    override fun updateBinding(viewModel: SetupViewModel) {
        contentBinding.apply {
            this.viewModel = viewModel
            executePendingBindings()
        }
    }
}
