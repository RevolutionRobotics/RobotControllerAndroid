package com.revolution.robotics.features.onboarding.haveyoubuilt

import com.revolution.robotics.core.domain.local.BuildStatus
import com.revolution.robotics.core.domain.local.UserConfiguration
import com.revolution.robotics.core.domain.local.UserRobot
import com.revolution.robotics.core.interactor.GetControllerTypeInteractor
import com.revolution.robotics.core.interactor.firebase.RobotInteractor
import com.revolution.robotics.core.kodein.utils.ResourceResolver
import com.revolution.robotics.core.utils.AppPrefs
import com.revolution.robotics.core.utils.CreateRobotInstanceHelper
import com.revolution.robotics.core.utils.Navigator
import com.revolution.robotics.features.build.BuildRobotFragment
import com.revolution.robotics.features.controllers.ControllerType
import com.revolution.robotics.features.myRobots.MyRobotsFragmentDirections
import com.revolution.robotics.features.shared.ErrorHandler
import java.util.*

class HaveYouBuiltPresenter(
    private val navigator: Navigator,
    private val resourceResolver: ResourceResolver,
    private val robotInteractor: RobotInteractor,
    private val getControllerTypeInteractor: GetControllerTypeInteractor,
    private val createRobotInstanceHelper: CreateRobotInstanceHelper,
    private val errorHandler: ErrorHandler,
    private val appPrefs: AppPrefs
) : HaveYouBuiltMvp.Presenter {

    companion object {
        private const val ROBOT_ID: String = "c92b9a90-e069-11e9-9d36-2a2ae2dbcce4"
    }

    override var view: HaveYouBuiltMvp.View? = null
    override var model: HaveYouBuiltViewModel? = null

    override fun driveRobot() {
        createRobot { userRobot ->
            userRobot.buildStatus = BuildStatus.COMPLETED
            createRobotInstanceHelper.setupConfigFromFirebase(userRobot,
                onSuccess = { savedRobot, _, _ ->
                    getControllerTypeInteractor.robotId = savedRobot.id
                    getControllerTypeInteractor.execute { type ->
                        appPrefs.onboardingRobotBuild = true
                        appPrefs.onboardingRobotDriven = true
                        when (type) {
                            ControllerType.GAMER ->
                                navigator.navigate(MyRobotsFragmentDirections.toPlayGamer(savedRobot.id))
                            ControllerType.MULTITASKER ->
                                navigator.navigate(MyRobotsFragmentDirections.toPlayMultitasker(savedRobot.id))
                            ControllerType.DRIVER ->
                                navigator.navigate(MyRobotsFragmentDirections.toPlayDriver(savedRobot.id))
                        }
                    }
                }, onError = {
                    errorHandler.onError(it)
                })
        }
    }

    override fun buildRobot() {
        createRobot { userRobot ->
            appPrefs.onboardingRobotBuild = true
            appPrefs.onboardingRobotDriven = true
            navigator.navigate(HaveYouBuiltFragmentDirections.toBuildRobot(userRobot))
        }
    }

    override fun skipOnboarding() {
        appPrefs.finishedOnboarding = true
        appPrefs.onboardingRobotBuild = true
        appPrefs.onboardingRobotDriven = true
        navigator.back()
    }

    private fun createRobot(onResponse: (UserRobot) -> Unit) {
        robotInteractor.robotId = ROBOT_ID
        robotInteractor.execute { robot ->
            val userRobot = UserRobot(
                0,
                robot.id,
                BuildStatus.IN_PROGRESS,
                BuildRobotFragment.DEFAULT_STARTING_INDEX,
                Date(System.currentTimeMillis()),
                UserConfiguration(),
                robot.name?.getLocalizedString(resourceResolver) ?: "",
                robot.coverImage,
                robot.description?.getLocalizedString(resourceResolver) ?: ""
            )
            onResponse(userRobot)
        }
    }
}