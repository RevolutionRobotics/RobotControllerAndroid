package com.revolution.robotics.features.mainmenu

import com.revolution.robotics.R
import com.revolution.robotics.core.interactor.GetUserProgramInteractor
import com.revolution.robotics.core.utils.AppPrefs
import com.revolution.robotics.core.utils.Navigator
import com.revolution.robotics.features.mainmenu.tutorial.TutorialViewModel

class MainMenuPresenter(
    private val userProgramInteractor: GetUserProgramInteractor,
    private val navigator: Navigator,
    private val appPrefs: AppPrefs
) : MainMenuMvp.Presenter {

    override var view: MainMenuMvp.View? = null
    override var model: MainMenuViewModel? = null

    var tutorialViewModel: TutorialViewModel? = null

    override fun register(view: MainMenuMvp.View, model: MainMenuViewModel?) {
        super.register(view, model)
        if (!appPrefs.userTypeSelected) {
            navigator.navigate(MainMenuFragmentDirections.toUserTypeSelection())
        } else if (!appPrefs.robotRegistered) {
            navigator.navigate(MainMenuFragmentDirections.toRobotRegistration())
        } else if (!appPrefs.finishedOnboarding) {
            if (!appPrefs.onboardingRobotBuild) {
                navigator.navigate(MainMenuFragmentDirections.toHaveYouBuiltCarby())
            } else {
                appPrefs.finishedOnboarding = true
                view.showCongratulationsDialog()
            }
        }
    }

    override fun navigateToMyRobots() {
        navigator.navigate(MainMenuFragmentDirections.toMyRobots())
    }

    override fun navigateToCoding() {
        userProgramInteractor.name = appPrefs.lastOpenedProgram
        userProgramInteractor.execute {
            navigator.navigate(MainMenuFragmentDirections.toCoding(it, false))
        }
    }

    override fun navigateToChallengeList() {
        navigator.navigate(MainMenuFragmentDirections.toChallengeList())
    }

    override fun onCommunityClicked() {
        navigator.navigate(R.id.toCommunity)
    }

    override fun onSettingsClicked() {
        navigator.navigate(MainMenuFragmentDirections.toSettings())
    }

    override fun onTutorialButtonClicked() {
        appPrefs.showTutorial = false
        tutorialViewModel?.tutorialItems?.forEach {
            it.isActive.set(false)
        }
        tutorialViewModel = null
        view?.removeTutorialLayout()
    }
}
