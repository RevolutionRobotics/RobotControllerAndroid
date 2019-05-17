package com.revolution.robotics.features.controllers.setup.instances

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.revolution.robotics.databinding.FragmentControllerSetupDriverBinding
import com.revolution.robotics.features.controllers.setup.SetupFragment
import com.revolution.robotics.features.controllers.setup.SetupViewModel

class SetupDriverFragment : SetupFragment() {

    private lateinit var contentBinding: FragmentControllerSetupDriverBinding

    override fun createContentView(inflater: LayoutInflater, container: ViewGroup?): View {
        contentBinding = FragmentControllerSetupDriverBinding.inflate(inflater, container, true).apply {
            viewModel = this@SetupDriverFragment.viewModel
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
