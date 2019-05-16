package com.revolution.robotics.features.controllers.controllerTypeSelector

import com.revolution.robotics.BaseFragment
import com.revolution.robotics.R
import com.revolution.robotics.databinding.FragmentControllerTypeSelectorBinding

class ControllerTypeSelectorFragment :
    BaseFragment<FragmentControllerTypeSelectorBinding, ControllerTypeSelectorViewModel>
        (R.layout.fragment_controller_type_selector), ControllerTypeSelectorMvp.View {

    override val viewModelClass = ControllerTypeSelectorViewModel::class.java
}
