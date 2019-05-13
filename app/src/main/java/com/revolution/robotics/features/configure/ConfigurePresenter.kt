package com.revolution.robotics.features.configure

import com.revolution.robotics.core.domain.local.UserConfiguration
import com.revolution.robotics.core.domain.local.UserRobot
import com.revolution.robotics.core.eventBus.dialog.DialogEvent
import com.revolution.robotics.core.eventBus.dialog.DialogEventBus
import com.revolution.robotics.core.interactor.GetUserConfigurationInteractor
import com.revolution.robotics.core.interactor.SaveUserRobotInteractor
import com.revolution.robotics.features.configure.save.SaveRobotDialog

class ConfigurePresenter(
    private val configurationEventBus: ConfigurationEventBus,
    private val getUserConfigurationInteractor: GetUserConfigurationInteractor,
    private val saveUserRobotInteractor: SaveUserRobotInteractor,
    private val dialogEventBus: DialogEventBus
) : ConfigureMvp.Presenter,
    ConfigurationEventBus.Listener, DialogEventBus.Listener {

    override var model: ConfigureViewModel? = null
    override var view: ConfigureMvp.View? = null

    private var toolbarViewModel: ConfigureToolbarViewModel? = null

    var userConfiguration: UserConfiguration? = null
    var userRobot: UserRobot? = null

    override fun register(view: ConfigureMvp.View, model: ConfigureViewModel?) {
        super.register(view, model)
        configurationEventBus.register(this)
        dialogEventBus.register(this)
    }

    override fun initUI(userRobot: UserRobot, toolbarViewModel: ConfigureToolbarViewModel) {
        this.userRobot = userRobot
        this.toolbarViewModel = toolbarViewModel
        toolbarViewModel.title.set(userRobot.name)
        getUserConfigurationInteractor.userConfigId = userRobot.configurationId
        getUserConfigurationInteractor.execute(
            onResponse = { config ->
                userConfiguration = config
                userConfiguration?.apply {
                    model?.setScreen(ConfigurationTabs.CONNECTIONS)
                    view?.showConnectionsScreen(this)
                }
            },
            onError = { error ->
                // TODO Error handling
            })
    }

    override fun unregister() {
        configurationEventBus.unregister(this)
        dialogEventBus.unregister(this)
        super.unregister()
    }

    override fun onDialogEvent(event: DialogEvent) {
        if (event == DialogEvent.SAVE_ROBOT) {
            userRobot?.let { robot ->
                robot.name = event.extras.getString(SaveRobotDialog.KEY_NAME)
                robot.description = event.extras.getString(SaveRobotDialog.KEY_DESCRIPTION)
                saveUserRobotInteractor.userConfiguration = userConfiguration
                saveUserRobotInteractor.userRobot = robot
                toolbarViewModel?.title?.set(robot.name)
                saveUserRobotInteractor.execute(onResponse = {},
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
        userConfiguration?.let { view?.showConnectionsScreen(it) }
    }

    override fun onControllerTabSelected() {
        model?.setScreen(ConfigurationTabs.CONTROLLERS)
        view?.showControllerScreen()
    }

    override fun onMotorConfigChangedEvent(event: MotorPort) {
        userConfiguration?.let { config ->
            config.mappingId?.updateMotorPort(event)
            view?.updateConfig(config)
        }
        view?.hideDrawer()
    }

    override fun onSensorConfigChangedEvent(event: SensorPort) {
        userConfiguration?.let { config ->
            config.mappingId?.updateSensorPort(event)
            view?.updateConfig(config)
        }
        view?.hideDrawer()
    }

    override fun onRobotImageClicked() {
        userRobot?.instanceId?.let { view?.showDialog(RobotPictureDialog.newInstance(it)) }
    }

    override fun saveConfiguration() {
        userRobot?.let { robot ->
            view?.showSaveDialog(robot.name ?: "", robot.description ?: "")
        }
    }
}
