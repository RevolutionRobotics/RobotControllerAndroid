package com.revolution.robotics.features.mainmenu

import androidx.lifecycle.ViewModel
import com.revolution.robotics.R

class MainMenuViewModel(private val presenter: MainMenuMvp.Presenter) : ViewModel() {

    val robotButton = MainMenuButtonViewModel(
        R.string.menu_robots,
        R.drawable.bg_card_border_red_selector,
        R.drawable.robots,
        presenter::navigateToMyRobots
    )

    val codingButton = MainMenuButtonViewModel(
        R.string.menu_coding,
        R.drawable.bg_card_border_yellow_selector,
        R.drawable.coding,
        presenter::navigateToCoding
    )

    val challengesButton = MainMenuButtonViewModel(
        R.string.menu_challenges,
        R.drawable.bg_card_border_blue_selector,
        R.drawable.challenges,
        presenter::navigateToChallengeList
    )

    fun onSettingsClicked() {
        presenter.onSettingsClicked()
    }

    fun onCommunityClicked() {
        presenter.onCommunityClicked()
    }
}
