package com.revolution.robotics.features.coding

import androidx.databinding.ObservableField
import androidx.lifecycle.ViewModel
import com.revolution.robotics.R
import com.revolution.robotics.core.kodein.utils.ResourceResolver

class CodingViewModel(resourceResolver: ResourceResolver) : ViewModel() {

    val programName = ObservableField<String>(resourceResolver.string(R.string.program_title_default))
}
