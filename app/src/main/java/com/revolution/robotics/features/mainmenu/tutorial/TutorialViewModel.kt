package com.revolution.robotics.features.mainmenu.tutorial

import androidx.databinding.ObservableBoolean

class TutorialViewModel {

    val tutorialItems = listOf(
        TutorialItemViewModel(TutorialItem.ROBOTS),
        TutorialItemViewModel(TutorialItem.PROGRAMS),
        TutorialItemViewModel(TutorialItem.CHALLENGES),
        TutorialItemViewModel(TutorialItem.COMMUNITY),
        TutorialItemViewModel(TutorialItem.SETTINGS)
    )

    private var selectedIndex = 0
    val isPreviousButtonEnabled = ObservableBoolean()
    val isNextButtonEnabled = ObservableBoolean()

    init {
        tutorialItems[0].isActive.set(true)
        isPreviousButtonEnabled.set(false)
        isNextButtonEnabled.set(true)
    }

    fun onPreviousButtonClicked() {
        if (selectedIndex > 0) {
            tutorialItems[selectedIndex].isActive.set(false)
            selectedIndex--
            tutorialItems[selectedIndex].isActive.set(true)
        }
        isPreviousButtonEnabled.set(selectedIndex != 0)
        isNextButtonEnabled.set(selectedIndex != tutorialItems.size - 1)
    }

    fun onNextButtonClicked() {
        if (selectedIndex < tutorialItems.size - 1) {
            tutorialItems[selectedIndex].isActive.set(false)
            selectedIndex++
            tutorialItems[selectedIndex].isActive.set(true)
        }

        isPreviousButtonEnabled.set(selectedIndex != 0)
        isNextButtonEnabled.set(selectedIndex != tutorialItems.size - 1)
    }
}
