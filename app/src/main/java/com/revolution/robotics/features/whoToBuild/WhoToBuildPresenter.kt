package com.revolution.robotics.features.whoToBuild

import android.os.Bundle
import android.util.Log
import com.revolution.robotics.analytics.Reporter
import com.revolution.robotics.core.cache.ImageCache
import com.revolution.robotics.core.domain.PortMapping
import com.revolution.robotics.core.domain.local.BuildStatus
import com.revolution.robotics.core.domain.local.UserConfiguration
import com.revolution.robotics.core.domain.local.UserController
import com.revolution.robotics.core.domain.local.UserRobot
import com.revolution.robotics.core.domain.remote.Robot
import com.revolution.robotics.core.extensions.isEmptyOrNull
import com.revolution.robotics.core.interactor.AssignConfigToRobotInteractor
import com.revolution.robotics.core.interactor.SaveUserControllerInteractor
import com.revolution.robotics.core.interactor.SaveUserRobotInteractor
import com.revolution.robotics.core.interactor.api.DownloadRobotInteractor
import com.revolution.robotics.core.interactor.api.DownloadRobotsInteractor
import com.revolution.robotics.core.interactor.firebase.FirebaseFileDownloader
import com.revolution.robotics.core.interactor.firebase.RobotsInteractor
import com.revolution.robotics.core.kodein.utils.ResourceResolver
import com.revolution.robotics.core.utils.Navigator
import com.revolution.robotics.features.build.BuildRobotFragment
import com.revolution.robotics.features.controllers.ControllerType
import com.revolution.robotics.features.whoToBuild.adapter.RobotsBuildYourOwnItem
import com.revolution.robotics.features.whoToBuild.adapter.RobotsItem
import java.util.Date
import kotlin.collections.emptyList
import kotlin.collections.firstOrNull
import kotlin.collections.indexOfFirst
import kotlin.collections.isNotEmpty
import kotlin.collections.map
import kotlin.collections.toMutableList
import kotlin.math.max

class WhoToBuildPresenter(
    private val downloadRobotsInteractor: DownloadRobotsInteractor,
    private val robotsInteractor: RobotsInteractor,
    private val assignConfigToRobotInteractor: AssignConfigToRobotInteractor,
    private val saveUserRobotInteractor: SaveUserRobotInteractor,
    private val saveUserControllerInteractor: SaveUserControllerInteractor,
    private val downloadRobotInteractor: DownloadRobotInteractor,
    private val resourceResolver: ResourceResolver,
    private val imageCache: ImageCache,
    private val navigator: Navigator,
    private val reporter: Reporter
) :
    WhoToBuildMvp.Presenter {

    override var model: WhoToBuildViewModel? = null
    override var view: WhoToBuildMvp.View? = null

    override fun register(view: WhoToBuildMvp.View, model: WhoToBuildViewModel?) {
        super.register(view, model)
        refreshRobotList()
        loadRobots()
        displayRobots(emptyList())
    }

    private fun refreshRobotList() {
        downloadRobotsInteractor.execute(
            onResponse = { changed ->
                if (changed) {
                    loadRobots()
                }
            },
            onError = {}
        )
    }

    private fun loadRobots() {
        robotsInteractor.execute({ response ->
            displayRobots(response)
        }, { error ->
            Log.e("Error", error.localizedMessage)
        })
    }

    private fun displayRobots(robots: List<Robot>) {
        model?.apply {
            currentPosition.set(if (robots.isNotEmpty()) 1 else 0)
            robotsList.value =
                robots.map { robot ->
                    RobotsItem(
                        robot,
                        imageCache.getImagePath(robot.coverImage),
                        isDownloaded(robot),
                        this@WhoToBuildPresenter
                    )
                }
                    .toMutableList()
                    .apply { add(0, RobotsBuildYourOwnItem(this@WhoToBuildPresenter)) }
            robotsList.value?.firstOrNull()?.isSelected?.set(true)
            updateButtonsVisibility(0)
            view?.onRobotsLoaded()
        }
    }

    private fun isDownloaded(robot: Robot): Boolean {
        for (step in robot.buildSteps) {
            if (step.image != null && !imageCache.isSaved(step.image!!)) {
                return false
            }
        }
        return true
    }

    override fun onPageSelected(position: Int) {
        model?.run {
            val list = robotsList.value ?: return
            if (list.isNotEmpty()) {
                if (currentPosition.get() < list.size) {
                    list[currentPosition.get()].isSelected.set(false)
                }
                list[position].isSelected.set(true)
                currentPosition.set(position)
                updateButtonsVisibility(position)
            }
        }
    }

    private fun updateButtonsVisibility(position: Int) {
        model?.run {
            if (robotsList.value.isEmptyOrNull()) {
                isPreviousButtonVisible.set(false)
                isNextButtonVisible.set(false)
            } else {
                isPreviousButtonVisible.set(position != 0)
                isNextButtonVisible.set(position != max((robotsList.value?.size ?: 0) - 1, 0))
            }
        }
    }

    override fun nextButtonClick() {
        view?.showNextRobot()
    }

    override fun previousButtonClick() {
        view?.showPreviousRobot()
    }

    override fun onRobotSelected(robot: Robot) {
        if (isDownloaded(robot)) {
            createRobot(robot)
        } else {
            var start = System.currentTimeMillis()
            downloadRobotInteractor.robot = robot
            downloadRobotInteractor.execute {
                Log.d(
                    "ROBOTS",
                    robot.name?.en + " downloaded in " + (System.currentTimeMillis() - start) / 1000 + " sec"
                )
            }
        }
    }

    private fun createRobot(robot: Robot) {
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
        reporter.reportEvent(Reporter.Event.START_BASIC_ROBOT, Bundle().apply {
            putString(Reporter.Parameter.ID.parameterName, robot.id)
        })
        navigator.navigate(WhoToBuildFragmentDirections.toBuildRobot(userRobot))
    }

    override fun onBuildYourOwnSelected() {
        val userRobot = UserRobot(
            buildStatus = BuildStatus.COMPLETED,
            lastModified = Date(System.currentTimeMillis()),
            name = "",
            configuration = UserConfiguration()
        )
        reporter.reportEvent(Reporter.Event.CREATE_CUSTOM_ROBOT)
        saveUserRobotInteractor.userRobot = userRobot
        saveUserRobotInteractor.execute { savedRobot ->
            assignEmptyConfig(savedRobot)
        }
    }

    override fun onDisabledItemClicked(robotsItem: RobotsItem) {
        val index = model?.robotsList?.value?.indexOf(robotsItem) ?: 0
        if (index < model?.robotsList?.value?.indexOfFirst { it.isSelected.get() } ?: 0) {
            view?.showPreviousRobot()
        } else {
            view?.showNextRobot()
        }
    }

    private fun assignEmptyConfig(userRobot: UserRobot) {
        assignConfigToRobotInteractor.userRobot = userRobot
        assignConfigToRobotInteractor.portMapping = PortMapping()
        assignConfigToRobotInteractor.controller = null
        assignConfigToRobotInteractor.programs = emptyList()
        assignConfigToRobotInteractor.execute {
            createNewController(userRobot)
        }
    }

    private fun createNewController(userRobot: UserRobot) {
        val controller = UserController(robotId = userRobot.id, type = ControllerType.GAMER.id)
        saveUserControllerInteractor.userController = controller
        saveUserControllerInteractor.backgroundProgramBindings = emptyList()
        saveUserControllerInteractor.execute {
            navigator.navigate(WhoToBuildFragmentDirections.toConfigure(userRobot.id))
        }
    }
}
