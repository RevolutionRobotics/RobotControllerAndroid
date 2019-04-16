package com.revolution.robotics.mainmenu

import com.revolution.robotics.core.navigation.Navigator

class MainMenuPresenter(private val navigator: Navigator) : MainMenuMvp.Presenter {

    override var view: MainMenuMvp.View? = null
    override var model: MainMenuViewModel? = null

    override fun navigateToRobots() {
        navigator.navigate(MainMenuFragmentDirections.toRobots())
    }

    override fun navigateToCoding() {
        navigator.navigate(MainMenuFragmentDirections.toCoding())
    }

    override fun navigateToChallenges() {
        navigator.navigate(MainMenuFragmentDirections.toChallenges())
    }
}