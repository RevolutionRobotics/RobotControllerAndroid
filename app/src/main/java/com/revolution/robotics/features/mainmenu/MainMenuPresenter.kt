package com.revolution.robotics.features.mainmenu

import com.revolution.robotics.core.utils.Navigator

class MainMenuPresenter(private val navigator: Navigator) : MainMenuMvp.Presenter {
    override var view: MainMenuMvp.View? = null
    override var model: MainMenuViewModel? = null

    override fun navigateToMyRobots() {
        navigator.navigate(MainMenuFragmentDirections.toMyRobots())
    }

    override fun navigateToCoding() {
        navigator.navigate(MainMenuFragmentDirections.toCoding())
    }

    override fun navigateToChallenges() {
        navigator.navigate(MainMenuFragmentDirections.toChallenges())
    }

    override fun onCommunityClicked() {
        navigator.navigate(MainMenuFragmentDirections.toCommunity())
    }

    override fun onSettingsClicked() {
        navigator.navigate(MainMenuFragmentDirections.toSettings())
    }
}
