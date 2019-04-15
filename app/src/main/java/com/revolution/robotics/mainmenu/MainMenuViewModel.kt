package com.revolution.robotics.mainmenu

import androidx.lifecycle.ViewModel
import com.revolution.robotics.R

class MainMenuViewModel(private val presenter: MainMenuMvp.Presenter) : ViewModel(), MainMenuMvp.Model {

    override val robotButton = MainMenuButtonViewModel(
        R.string.menu_robots,
        R.drawable.card_border_red,
        R.drawable.ic_robots,
        presenter::navigateToRobots
    )

    override val codingButton = MainMenuButtonViewModel(
        R.string.menu_coding,
        R.drawable.card_border_yellow,
        R.drawable.ic_programs,
        presenter::navigateToCoding
    )

    override val challengesButton = MainMenuButtonViewModel(
        R.string.menu_challenges,
        R.drawable.card_border_blue,
        R.drawable.ic_challenges,
        presenter::navigateToChallenges
    )

    override fun onCommunityIconClicked() = Unit

    override fun onRoboticsIconClicked() = Unit
}
