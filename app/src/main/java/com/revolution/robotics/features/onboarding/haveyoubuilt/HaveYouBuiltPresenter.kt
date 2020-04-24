package com.revolution.robotics.features.onboarding.haveyoubuilt

import com.revolution.robotics.analytics.Reporter
import com.revolution.robotics.core.domain.local.BuildStatus
import com.revolution.robotics.core.domain.local.UserChallengeCategory
import com.revolution.robotics.core.domain.local.UserConfiguration
import com.revolution.robotics.core.domain.local.UserRobot
import com.revolution.robotics.core.interactor.GetControllerTypeInteractor
import com.revolution.robotics.core.interactor.SaveCompletedChallengeInteractor
import com.revolution.robotics.core.interactor.firebase.RobotInteractor
import com.revolution.robotics.core.kodein.utils.ResourceResolver
import com.revolution.robotics.core.utils.AppPrefs
import com.revolution.robotics.core.utils.CreateRobotInstanceHelper
import com.revolution.robotics.core.utils.Navigator
import com.revolution.robotics.features.build.BuildRobotFragment
import com.revolution.robotics.features.myRobots.MyRobotsFragmentDirections
import com.revolution.robotics.features.shared.ErrorHandler
import java.util.*

class HaveYouBuiltPresenter(
    private val navigator: Navigator,
    private val resourceResolver: ResourceResolver,
    private val robotInteractor: RobotInteractor,
    private val saveCompletedChallengeInteractor: SaveCompletedChallengeInteractor,
    private val createRobotInstanceHelper: CreateRobotInstanceHelper,
    private val errorHandler: ErrorHandler,
    private val appPrefs: AppPrefs,
    private val reporter: Reporter
) : HaveYouBuiltMvp.Presenter {

    companion object {
        private const val ROBOT_ID: String = "revvy"
        private val CHALLENGE_IDS = listOf("e32cbf06-3343-446b-a17f-af84e160cee5", "ce95005e-16e1-4d7b-ac9c-b24ba9b6625f")
    }

    override var view: HaveYouBuiltMvp.View? = null
    override var model: HaveYouBuiltViewModel? = null

    override fun driveRobot() {
        completeOnboardingChallenges()
        reporter.reportEvent(Reporter.Event.BUILD_BASIC_ROBOT_OFFLINE)
        createRobot { userRobot ->
            userRobot.buildStatus = BuildStatus.COMPLETED
            createRobotInstanceHelper.setupConfigFromFirebase(userRobot,
                onSuccess = { savedRobot ->
                    appPrefs.onboardingRobotBuild = true
                    appPrefs.onboardingRobotDriven = true
                    navigator.navigate(MyRobotsFragmentDirections.toPlay(savedRobot.id))
                }, onError = {
                    errorHandler.onError(it)
                })
        }
    }

    override fun buildRobot() {
        completeOnboardingChallenges()
        reporter.reportEvent(Reporter.Event.BUILD_BASIC_ROBOT_ONLINE)
        createRobot { userRobot ->
            appPrefs.onboardingRobotBuild = true
            appPrefs.onboardingRobotDriven = true
            navigator.navigate(HaveYouBuiltFragmentDirections.toBuildRobot(userRobot))
        }
    }

    override fun skipOnboarding() {
        reporter.reportEvent(Reporter.Event.SKIP_ONBOARDING)
        appPrefs.finishedOnboarding = true
        appPrefs.onboardingRobotBuild = true
        appPrefs.onboardingRobotDriven = true
        navigator.back()
    }

    private fun createRobot(onResponse: (UserRobot) -> Unit) {
        robotInteractor.robotId = ROBOT_ID
        robotInteractor.execute { robot ->
            robot?.apply {
                val userRobot = UserRobot(
                    0,
                    id,
                    BuildStatus.IN_PROGRESS,
                    BuildRobotFragment.DEFAULT_STARTING_INDEX,
                    Date(System.currentTimeMillis()),
                    UserConfiguration(),
                    name?.getLocalizedString(resourceResolver) ?: "",
                    coverImage,
                    description?.getLocalizedString(resourceResolver) ?: ""
                )
                onResponse(userRobot)
            }
        }
    }

    private fun completeOnboardingChallenges() {
        markCompleted(0)
    }

    private fun markCompleted(challengeIndex: Int) {
        saveCompletedChallengeInteractor.challengeId = CHALLENGE_IDS[challengeIndex]
        saveCompletedChallengeInteractor.execute() {
            if (challengeIndex < CHALLENGE_IDS.size - 1) {
                markCompleted(challengeIndex + 1)
            }
        }
    }
}