package com.revolution.robotics.mainmenu

import androidx.lifecycle.ViewModel
import com.revolution.robotics.R
import com.revolution.robotics.core.navigation.Navigator

class MainMenuViewModel(private val navigator: Navigator) : ViewModel() {

    val robotButton = MainMenuButtonViewModel(
        R.string.menu_robots,
        R.drawable.card_border_red,
        R.drawable.ic_robots
    ) { navigator.navigate(MainMenuFragmentDirections.toRobots()) }

    val programsButton = MainMenuButtonViewModel(
        R.string.menu_coding,
        R.drawable.card_border_yellow,
        R.drawable.ic_programs
    ) { navigator.navigate(MainMenuFragmentDirections.toCoding()) }

    val challengesButton = MainMenuButtonViewModel(
        R.string.menu_challenges,
        R.drawable.card_border_blue,
        R.drawable.ic_challenges
    ) { navigator.navigate(MainMenuFragmentDirections.toChallenges()) }

    fun onCommunityIconClicked() {
        // navigator.navigate(MainMenuFragmentDirections.toCommunity())
    }

    fun onRoboticsIconClicked() {
        // navigator.navigate(MainMenuFragmentDirections.toRobotics())
    }
}
