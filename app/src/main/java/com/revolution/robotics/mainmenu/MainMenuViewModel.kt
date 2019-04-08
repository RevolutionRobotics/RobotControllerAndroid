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

    fun onCommunityIconClicked() {
        // navigator.navigate(MainMenuFragmentDirections.toCommunity())
    }

    fun onRoboticsIconClicked() {
        // navigator.navigate(MainMenuFragmentDirections.toRobotics())
    }
}
