package com.revolution.robotics.features.controllers.typeSelector

import android.os.Bundle
import android.view.View
import com.revolution.robotics.BaseFragment
import com.revolution.robotics.R
import com.revolution.robotics.core.kodein.utils.ResourceResolver
import com.revolution.robotics.databinding.FragmentControllerTypeSelectorBinding
import org.kodein.di.erased.instance

class TypeSelectorFragment :
    BaseFragment<FragmentControllerTypeSelectorBinding, TypeSelectorViewModel>
        (R.layout.fragment_controller_type_selector), TypeSelectorMvp.View {

    override val viewModelClass = TypeSelectorViewModel::class.java

    private val resourceResolver: ResourceResolver by kodein.instance()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding?.toolbarViewModel = TypeSelectorToolbarViewModel(resourceResolver)
    }
}
