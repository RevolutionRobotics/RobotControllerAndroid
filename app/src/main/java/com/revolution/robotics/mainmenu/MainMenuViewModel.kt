package com.revolution.robotics.mainmenu

import androidx.lifecycle.ViewModel
import com.revolution.robotics.core.navigation.Navigator

class MainMenuViewModel(private val navigator: Navigator) : ViewModel() {

    fun onRobotsClicked() {
        navigator.navigate(MainMenuFragmentDirections.toRobots())
    }

    fun onCodingClicked() {
        navigator.navigate(MainMenuFragmentDirections.toCoding())
    }

    fun onChallengesClicked() {
        navigator.navigate(MainMenuFragmentDirections.toChallenges())
    }

    // Handle community navigation
    fun onCommunityIconClicked() {}

    // Handle robotics icon navigation
    fun onRoboticsIconClicked() {}
}
