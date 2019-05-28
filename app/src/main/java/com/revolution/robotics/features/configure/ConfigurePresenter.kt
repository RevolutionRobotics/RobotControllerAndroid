package com.revolution.robotics.features.configure

import com.revolution.robotics.R
import com.revolution.robotics.core.domain.local.UserConfiguration
import com.revolution.robotics.core.domain.local.UserRobot
import com.revolution.robotics.core.eventBus.dialog.DialogEvent
import com.revolution.robotics.core.eventBus.dialog.DialogEventBus
import com.revolution.robotics.core.interactor.GetUserConfigurationInteractor
import com.revolution.robotics.core.interactor.SaveUserRobotInteractor
import com.revolution.robotics.core.kodein.utils.ApplicationContextProvider
import com.revolution.robotics.core.kodein.utils.ResourceResolver
import com.revolution.robotics.core.utils.CameraHelper
import com.revolution.robotics.core.utils.Navigator
import com.revolution.robotics.features.configure.robotPicture.RobotPictureDialog
import com.revolution.robotics.features.configure.save.SaveRobotDialog

@Suppress("TooManyFunctions")
class ConfigurePresenter(
    private val configurationEventBus: ConfigurationEventBus,
    private val getUserConfigurationInteractor: GetUserConfigurationInteractor,
    private val saveUserRobotInteractor: SaveUserRobotInteractor,
    private val dialogEventBus: DialogEventBus,
    private val navigator: Navigator,
    private val userConfigurationStorage: UserConfigurationStorage,
    private val resourceResolver: ResourceResolver,
    private val applicationContextProvider: ApplicationContextProvider
) : ConfigureMvp.Presenter,
    ConfigurationEventBus.Listener, DialogEventBus.Listener {

    override var model: ConfigureViewModel? = null
    override var view: ConfigureMvp.View? = null

    private var toolbarViewModel: ConfigureToolbarViewModel? = null

    var userRobot: UserRobot? = null

    var selectedTab = ConfigurationTabs.CONNECTIONS
    var selectedConfigId = -1

    override fun register(view: ConfigureMvp.View, model: ConfigureViewModel?) {
        super.register(view, model)
        configurationEventBus.register(this)
        dialogEventBus.register(this)
    }

    override fun initUI(userRobot: UserRobot, toolbarViewModel: ConfigureToolbarViewModel) {
        this.userRobot = userRobot
        this.toolbarViewModel = toolbarViewModel
        toolbarViewModel.title.set(
            if (userRobot.name.isNullOrEmpty()) {
                resourceResolver.string(R.string.untitled_robot_name)
            } else {
                userRobot.name
            }
        )

        if (userConfigurationStorage.userConfiguration == null ||
            userConfigurationStorage.userConfiguration?.id != userRobot.configurationId
        ) {
            getUserConfigurationInteractor.userConfigId = userRobot.configurationId
            getUserConfigurationInteractor.execute(
                onResponse = { config ->
                    onConfigurationLoaded(config)
                },
                onError = {
                    // TODO Error handling
                })
        } else {
            onConfigurationLoaded(userConfigurationStorage.userConfiguration)
        }
    }

    private fun onConfigurationLoaded(config: UserConfiguration?) {
        userConfigurationStorage.userConfiguration = config
        config?.apply {
            if (selectedConfigId != config.id) {
                selectedTab = ConfigurationTabs.CONNECTIONS
                selectedConfigId = config.id
            }
            model?.setScreen(selectedTab)

            if (selectedTab == ConfigurationTabs.CONNECTIONS) {
                view?.showConnectionsScreen()
            } else {
                view?.showControllerScreen()
            }
        }
    }

    override fun unregister(view: ConfigureMvp.View?) {
        configurationEventBus.unregister(this)
        dialogEventBus.unregister(this)
        super.unregister(view)
    }

    override fun onDialogEvent(event: DialogEvent) {
        if (event == DialogEvent.SAVE_ROBOT) {
            userRobot?.let { robot ->
                userConfigurationStorage.userConfiguration?.let { config ->
                    robot.name = event.extras.getString(SaveRobotDialog.KEY_NAME)
                    robot.description = event.extras.getString(SaveRobotDialog.KEY_DESCRIPTION)
                    saveUserRobotInteractor.userConfiguration = config
                    saveUserRobotInteractor.userRobot = robot
                    toolbarViewModel?.title?.set(robot.name)
                    saveUserRobotInteractor.execute(
                        onResponse = { savedRobotId ->
                            selectedTab = ConfigurationTabs.CONNECTIONS
                            selectedConfigId = -1
                            userConfigurationStorage.userConfiguration = null
                            userConfigurationStorage.controllerHolder = null
                            updateRobotImage(robot.instanceId, savedRobotId.toInt())
                            navigator.popUntil(R.id.myRobotsFragment)
                        },
                        onError = {
                            // TODO error handling
                        })
                }
            }
        }
    }

    private fun updateRobotImage(robotId: Int, savedRobotId: Int) {
        val context = applicationContextProvider.applicationContext
        val cameraHelper = CameraHelper(robotId)
        val dirtyImage = cameraHelper.getDirtyImageFile(context)
        val savedImage =
            if (robotId == 0) {
                CameraHelper(savedRobotId).getSavedImageFile(context)
            } else {
                cameraHelper.getSavedImageFile(context)
            }

        if (userConfigurationStorage.deleteRobotImage) {
            savedImage.delete()
        } else if (dirtyImage.exists()) {
            savedImage.delete()
            dirtyImage.renameTo(savedImage)
        }
    }

    override fun onOpenMotorConfigEvent(event: MotorPort) {
        view?.openMotorConfig(event)
    }

    override fun onOpenSensorConfigEvent(event: SensorPort) {
        view?.openSensorConfig(event)
    }

    override fun onConnectionsTabSelected() {
        selectedTab = ConfigurationTabs.CONNECTIONS
        model?.setScreen(ConfigurationTabs.CONNECTIONS)
        view?.showConnectionsScreen()
    }

    override fun onControllerTabSelected() {
        selectedTab = ConfigurationTabs.CONTROLLERS
        model?.setScreen(ConfigurationTabs.CONTROLLERS)
        view?.showControllerScreen()
    }

    override fun clearStorage() {
        userConfigurationStorage.userConfiguration = null
        userConfigurationStorage.controllerHolder = null
    }

    override fun onMotorConfigChangedEvent(event: MotorPort) {
        userConfigurationStorage.userConfiguration?.let { config ->
            config.mappingId?.updateMotorPort(event)
            view?.updateConfig(config)
        }
        view?.hideDrawer()
    }

    override fun onSensorConfigChangedEvent(event: SensorPort) {
        userConfigurationStorage.userConfiguration?.let { config ->
            config.mappingId?.updateSensorPort(event)
            view?.updateConfig(config)
        }
        view?.hideDrawer()
    }

    override fun onRobotImageClicked() {
        userRobot?.let { robot -> view?.showDialog(RobotPictureDialog.newInstance(robot.instanceId, robot.coverImage)) }
    }

    override fun saveConfiguration() {
        userRobot?.let { robot ->
            view?.showSaveDialog(robot.name ?: "", robot.description ?: "")
        }
    }
}
