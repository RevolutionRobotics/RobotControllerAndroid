package com.revolution.robotics.features.mainmenu.tutorial

import androidx.databinding.ObservableBoolean
import com.revolution.robotics.R
import com.revolution.robotics.views.chippedBox.ChippedBoxConfig

data class TutorialItemViewModel(val tutorialItem: TutorialItem) {
    val background = ChippedBoxConfig.Builder()
        .backgroundColorResource(R.color.grey_28)
        .chipBottomLeft(true)
        .chipTopRight(true)
        .chipSize(R.dimen.dimen_8dp)
        .borderColorResource(R.color.grey_28)
        .create()
    val isActive = ObservableBoolean()
}
