package com.revolution.robotics.features.configure

import com.revolution.robotics.R
import com.revolution.robotics.core.domain.local.UserConfiguration
import com.revolution.robotics.core.domain.local.UserMapping
import com.revolution.robotics.core.domain.local.UserRobot
import com.revolution.robotics.core.eventBus.dialog.DialogEvent
import com.revolution.robotics.core.eventBus.dialog.DialogEventBus
import com.revolution.robotics.core.interactor.GetUserConfigurationInteractor
import com.revolution.robotics.core.interactor.SaveUserRobotInteractor
import com.revolution.robotics.core.kodein.utils.ResourceResolver
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
    private val resourceResolver: ResourceResolver
) : ConfigureMvp.Presenter,
    ConfigurationEventBus.Listener, DialogEventBus.Listener {

    override var model: ConfigureViewModel? = null
    override var view: ConfigureMvp.View? = null

    private var toolbarViewModel: ConfigureToolbarViewModel? = null

    var userRobot: UserRobot? = null

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
        if (userRobot.configurationId == ConfigureFragment.CONFIG_ID_EMPTY) {
            onConfigurationLoaded(UserConfiguration().apply {
                mappingId = UserMapping()
            })
        } else {
            getUserConfigurationInteractor.userConfigId = userRobot.configurationId
            getUserConfigurationInteractor.execute(
                onResponse = { config ->
                    onConfigurationLoaded(config)
                },
                onError = {
                    // TODO Error handling
                })
        }
    }

    private fun onConfigurationLoaded(config: UserConfiguration?) {
        userConfigurationStorage.userConfiguration = config
        config?.apply {
            model?.setScreen(ConfigurationTabs.CONNECTIONS)
            view?.showConnectionsScreen(this)
        }
    }

    override fun unregister() {
        configurationEventBus.unregister(this)
        dialogEventBus.unregister(this)
        userConfigurationStorage.userConfiguration = null
        super.unregister()
    }

    override fun onDialogEvent(event: DialogEvent) {
        if (event == DialogEvent.SAVE_ROBOT) {
            userRobot?.let { robot ->
                robot.name = event.extras.getString(SaveRobotDialog.KEY_NAME)
                robot.description = event.extras.getString(SaveRobotDialog.KEY_DESCRIPTION)
                saveUserRobotInteractor.userConfiguration = userConfigurationStorage.userConfiguration
                saveUserRobotInteractor.userRobot = robot
                toolbarViewModel?.title?.set(robot.name)
                saveUserRobotInteractor.execute(
                    onResponse = {
                        navigator.popUntil(R.id.myRobotsFragment)
                    },
                    onError = {
                        // TODO error handling
                    })
            }
        }
    }

    override fun onOpenMotorConfigEvent(event: MotorPort) {
        view?.openMotorConfig(event)
    }

    override fun onOpenSensorConfigEvent(event: SensorPort) {
        view?.openSensorConfig(event)
    }

    override fun onConnectionsTabSelected() {
        model?.setScreen(ConfigurationTabs.CONNECTIONS)
        userConfigurationStorage.userConfiguration?.let { view?.showConnectionsScreen(it) }
    }

    override fun onControllerTabSelected() {
        model?.setScreen(ConfigurationTabs.CONTROLLERS)
        view?.showControllerScreen()
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
