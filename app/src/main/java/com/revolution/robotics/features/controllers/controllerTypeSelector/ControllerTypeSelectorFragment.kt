package com.revolution.robotics.features.controllers.controllerTypeSelector

import android.os.Bundle
import android.view.View
import com.revolution.robotics.BaseFragment
import com.revolution.robotics.R
import com.revolution.robotics.core.kodein.utils.ResourceResolver
import com.revolution.robotics.databinding.FragmentControllerTypeSelectorBinding
import org.kodein.di.erased.instance

class ControllerTypeSelectorFragment :
    BaseFragment<FragmentControllerTypeSelectorBinding, ControllerTypeSelectorViewModel>
        (R.layout.fragment_controller_type_selector), ControllerTypeSelectorMvp.View {

    override val viewModelClass = ControllerTypeSelectorViewModel::class.java

    private val resourceResolver: ResourceResolver by kodein.instance()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding?.toolbarViewModel = ControllerTypeSelectorToolbarViewModel(resourceResolver)
    }
}
