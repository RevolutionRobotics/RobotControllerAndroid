package com.revolution.robotics.mainmenu

import androidx.lifecycle.ViewModel
import com.revolution.robotics.core.navigation.NavigationEventBus

class MainMenuViewModel(private val navigationEventBus: NavigationEventBus) : ViewModel() {

    fun onRobotsClicked() {
        navigationEventBus.publishEvent(MainMenuFragmentDirections.actionMainMenuFragmentToRobotsFragment())
    }

    fun onCodingClicked() {
        navigationEventBus.publishEvent(MainMenuFragmentDirections.actionMainMenuFragmentToCodingFragment())
    }

    fun onChallengesClicked() {
        navigationEventBus.publishEvent(MainMenuFragmentDirections.actionMainMenuFragmentToChallengesFragment())
    }

    // Handle community navigation
    fun onCommunityIconClicked() {
    }

    // Handle robotics icon navigation
    fun onRoboticsIconClicked() {
    }

}
