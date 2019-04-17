package com.revolution.robotics.core.views.toolbar

interface ToolbarViewModel {

    val isLogoVisible: Boolean
    val hasBackOption: Boolean
    val title: String?
    val options: List<ToolbarOption>
}
