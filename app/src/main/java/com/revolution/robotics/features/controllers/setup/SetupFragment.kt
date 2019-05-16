package com.revolution.robotics.features.controllers.setup

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.revolution.robotics.BR
import com.revolution.robotics.BaseFragment
import com.revolution.robotics.R
import com.revolution.robotics.core.kodein.utils.ResourceResolver
import com.revolution.robotics.databinding.FragmentControllerSetupCoreBinding
import org.kodein.di.erased.instance

abstract class SetupFragment :
    BaseFragment<FragmentControllerSetupCoreBinding, SetupViewModel>(R.layout.fragment_controller_setup_core),
    SetupMvp.View {

    override val viewModelClass = SetupViewModel::class.java

    private val presenter: SetupMvp.Presenter by kodein.instance()
    private val resourceResolver: ResourceResolver by kodein.instance()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val core = super.onCreateView(inflater, container, savedInstanceState)
        createContentView(inflater, binding?.contentWrapper)
        return core
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        presenter.register(this, viewModel)
        binding?.toolbarViewModel = SetupToolbarViewModel(resourceResolver)
    }

    override fun onDestroyView() {
        presenter.unregister()
        super.onDestroyView()
    }

    override fun onProgramSlotSelected(index: Int, viewModel: SetupViewModel) {
        updateBinding(viewModel)
        // TODO open program selector
    }

    abstract fun createContentView(inflater: LayoutInflater, container: ViewGroup?): View
    abstract fun updateBinding(viewModel: SetupViewModel)
}