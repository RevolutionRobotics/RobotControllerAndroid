package com.revolution.robotics.features.mainmenu

import com.revolution.robotics.core.kodein.utils.ResourceResolver
import com.revolution.robotics.core.utils.AppPrefs
import com.revolution.robotics.core.utils.Navigator
import com.revolution.robotics.features.mainmenu.tutorial.TutorialItem
import com.revolution.robotics.features.mainmenu.tutorial.TutorialViewModel

class MainMenuPresenter(
    private val navigator: Navigator,
    private val appPrefs: AppPrefs,
    private val resourceResolver: ResourceResolver
) : MainMenuMvp.Presenter {

    override var view: MainMenuMvp.View? = null
    override var model: MainMenuViewModel? = null

    var tutorialViewModel: TutorialViewModel? = null

    override fun register(view: MainMenuMvp.View, model: MainMenuViewModel?) {
        super.register(view, model)
        if (appPrefs.showTutorial) {
            tutorialViewModel = TutorialViewModel(resourceResolver, this).apply {
                view.createTutorialLayout(this)
            }
        }
    }

    override fun navigateToMyRobots() {
        if (tutorialViewModel?.getSelectedTutorialItem() != TutorialItem.ROBOTS) {
            navigator.navigate(MainMenuFragmentDirections.toMyRobots())
        }
    }

    override fun navigateToCoding() {
        if (tutorialViewModel?.getSelectedTutorialItem() != TutorialItem.PROGRAMS) {
            navigator.navigate(MainMenuFragmentDirections.toCoding())
        }
    }

    override fun navigateToChallengeList() {
        if (tutorialViewModel?.getSelectedTutorialItem() != TutorialItem.CHALLENGES) {
            navigator.navigate(MainMenuFragmentDirections.toChallengeList())
        }
    }

    override fun onCommunityClicked() {
        if (tutorialViewModel?.getSelectedTutorialItem() != TutorialItem.COMMUNITY) {
            navigator.navigate(MainMenuFragmentDirections.toCommunity())
        }
    }

    override fun onSettingsClicked() {
        if (tutorialViewModel?.getSelectedTutorialItem() != TutorialItem.SETTINGS) {
            navigator.navigate(MainMenuFragmentDirections.toSettings())
        }
    }

    override fun onTutorialButtonClicked() {
        appPrefs.showTutorial = false
        tutorialViewModel = null
        view?.removeTutorialLayout()
    }
}
