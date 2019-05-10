package com.revolution.robotics.views.toolbar

import androidx.databinding.ObservableField

abstract class ToolbarViewModel {

    val title = ObservableField<String?>()

    abstract val isLogoVisible: Boolean
    abstract val hasBackOption: Boolean
    abstract val options: List<ToolbarOption>
}
