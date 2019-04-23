package com.revolution.robotics.views.toolbar

interface ToolbarViewModel {

    val isLogoVisible: Boolean
    val hasBackOption: Boolean
    val title: String?
    val options: List<ToolbarOption>
}
