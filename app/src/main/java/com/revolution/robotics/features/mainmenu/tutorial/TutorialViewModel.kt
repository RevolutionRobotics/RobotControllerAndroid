package com.revolution.robotics.features.mainmenu.tutorial

import android.graphics.drawable.Drawable
import androidx.databinding.ObservableBoolean
import androidx.databinding.ObservableField
import com.revolution.robotics.R
import com.revolution.robotics.core.kodein.utils.ResourceResolver
import com.revolution.robotics.features.mainmenu.MainMenuMvp
import com.revolution.robotics.views.chippedBox.ChippedBoxConfig

class TutorialViewModel(private val resourceResolver: ResourceResolver, private val presenter: MainMenuMvp.Presenter) {

    companion object {
        private val BACKGROUND_SKIP = ChippedBoxConfig.Builder()
            .chipSize(R.dimen.dimen_8dp)
            .backgroundColorResource(R.color.black)
            .borderColorResource(R.color.white)
            .borderSize(R.dimen.dimen_1dp)
            .create()

        private val BACKGROUND_FINISH = ChippedBoxConfig.Builder()
            .chipSize(R.dimen.dimen_8dp)
            .backgroundColorResource(R.color.grey_28)
            .borderColorResource(R.color.white)
            .borderSize(R.dimen.dimen_1dp)
            .create()
    }

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

    val buttonText = ObservableField<String>()
    val buttonImage = ObservableField<Drawable>()
    val buttonBackground = ObservableField<ChippedBoxConfig>()

    init {
        tutorialItems[0].isActive.set(true)
        setButtons()
    }

    fun getSelectedTutorialItem(): TutorialItem = tutorialItems[selectedIndex].tutorialItem

    fun onPreviousButtonClicked() {
        if (selectedIndex > 0) {
            tutorialItems[selectedIndex].isActive.set(false)
            selectedIndex--
            tutorialItems[selectedIndex].isActive.set(true)
        }
        setButtons()
    }

    fun onNextButtonClicked() {
        if (selectedIndex < tutorialItems.size - 1) {
            tutorialItems[selectedIndex].isActive.set(false)
            selectedIndex++
            tutorialItems[selectedIndex].isActive.set(true)
        }
        setButtons()
    }

    private fun setButtons() {
        isPreviousButtonEnabled.set(selectedIndex != 0)
        isNextButtonEnabled.set(selectedIndex != tutorialItems.size - 1)
        buttonText.set(
            if (selectedIndex == tutorialItems.size - 1) {
                resourceResolver.string(R.string.tutorial_button_finish)
            } else {
                resourceResolver.string(R.string.tutorial_button_skip)
            }
        )
        buttonImage.set(
            if (selectedIndex == tutorialItems.size - 1) {
                resourceResolver.drawable(R.drawable.ic_check)
            } else {
                resourceResolver.drawable(R.drawable.ic_skip)
            }
        )
        buttonBackground.set(
            if (selectedIndex == tutorialItems.size - 1) {
                BACKGROUND_FINISH
            } else {
                BACKGROUND_SKIP
            }
        )
    }

    fun onDoneButtonClicked() {
        presenter.onTutorialButtonClicked()
    }

    fun onOverlayClicked() {}
}
