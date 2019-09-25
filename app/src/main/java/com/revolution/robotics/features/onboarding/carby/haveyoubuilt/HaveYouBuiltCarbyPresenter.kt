package com.revolution.robotics.features.onboarding.carby.haveyoubuilt

import com.revolution.robotics.core.domain.local.BuildStatus
import com.revolution.robotics.core.domain.local.UserRobot
import com.revolution.robotics.core.interactor.firebase.RobotInteractor
import com.revolution.robotics.core.kodein.utils.ResourceResolver
import com.revolution.robotics.core.utils.Navigator
import com.revolution.robotics.features.build.BuildRobotFragment
import java.util.*

class HaveYouBuiltCarbyPresenter(
    private val navigator: Navigator,
    private val resourceResolver: ResourceResolver,
    private val robotInteractor: RobotInteractor
) : HaveYouBuiltCarbyMvp.Presenter {

    companion object {
        private const val CARBY_ID: String = "2d9b67e-804e-4022-8cae-5a26071fa275"
    }

    override var view: HaveYouBuiltCarbyMvp.View? = null
    override var model: HaveYouBuiltCarbyViewModel? = null

    override fun driveCarby() {

    }

    override fun buildCarby() {
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
            navigator.navigate(
                HaveYouBuiltCarbyFragmentDirections.toBuildRobot(
                    userRobot
                )
            )
        }
    }

    override fun skipOnboarding() {

    }


}