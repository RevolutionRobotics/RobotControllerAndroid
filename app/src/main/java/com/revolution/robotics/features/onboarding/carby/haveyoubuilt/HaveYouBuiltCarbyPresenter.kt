package com.revolution.robotics.features.onboarding.carby.haveyoubuilt

import com.revolution.robotics.core.domain.local.BuildStatus
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

class HaveYouBuiltCarbyPresenter(
    private val navigator: Navigator,
    private val resourceResolver: ResourceResolver,
    private val robotInteractor: RobotInteractor,
    private val getControllerTypeInteractor: GetControllerTypeInteractor,
    private val createRobotInstanceHelper: CreateRobotInstanceHelper,
    private val errorHandler: ErrorHandler,
    private val appPrefs: AppPrefs
) : HaveYouBuiltCarbyMvp.Presenter {

    companion object {
        private const val CARBY_ID: String = "2d9b67e-804e-4022-8cae-5a26071fa275"
    }

    override var view: HaveYouBuiltCarbyMvp.View? = null
    override var model: HaveYouBuiltCarbyViewModel? = null

    override fun driveCarby() {
        createCarby { userRobot ->
            userRobot.buildStatus = BuildStatus.COMPLETED
            createRobotInstanceHelper.setupConfigFromFirebase(userRobot,
                onSuccess = { savedRobot, configuration, controllers ->
                    getControllerTypeInteractor.configurationId = savedRobot.configurationId
                    getControllerTypeInteractor.execute { type ->
                        appPrefs.carbyBuild = true
                        appPrefs.carbyDriven = true
                        when (type) {
                            ControllerType.GAMER ->
                                navigator.navigate(MyRobotsFragmentDirections.toPlayGamer(savedRobot.configurationId))
                            ControllerType.MULTITASKER ->
                                navigator.navigate(MyRobotsFragmentDirections.toPlayMultitasker(savedRobot.configurationId))
                            ControllerType.DRIVER ->
                                navigator.navigate(MyRobotsFragmentDirections.toPlayDriver(savedRobot.configurationId))
                        }
                    }
                }, onError = {
                    errorHandler.onError()
                })
        }
    }

    override fun buildCarby() {
        createCarby { userRobot ->
            appPrefs.carbyBuild = true
            appPrefs.carbyDriven = true
            navigator.navigate(
                HaveYouBuiltCarbyFragmentDirections.toBuildRobot(
                    userRobot
                )
            )
        }
    }

    override fun skipOnboarding() {
        appPrefs.finishedOnboarding = true
        appPrefs.carbyBuild = true
        appPrefs.carbyDriven = true
        navigator.back()
    }

    private fun createCarby(onResponse: (UserRobot) -> Unit) {
        robotInteractor.robotId = CARBY_ID
        robotInteractor.execute { robot ->
            val userRobot = UserRobot(
                0,
                robot.id,
                BuildStatus.IN_PROGRESS,
                BuildRobotFragment.DEFAULT_STARTING_INDEX,
                Date(System.currentTimeMillis()),
                0,
                robot.name?.getLocalizedString(resourceResolver) ?: "",
                robot.coverImage,
                robot.description?.getLocalizedString(resourceResolver) ?: ""
            )
            onResponse(userRobot)
        }
    }
}