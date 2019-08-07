package com.revolution.robotics.features.configure

import com.revolution.robotics.R
import com.revolution.robotics.core.domain.local.UserConfiguration
import com.revolution.robotics.core.domain.local.UserRobot
import com.revolution.robotics.core.eventBus.dialog.DialogEvent
import com.revolution.robotics.core.eventBus.dialog.DialogEventBus
import com.revolution.robotics.core.interactor.*
import com.revolution.robotics.core.kodein.utils.ResourceResolver
import com.revolution.robotics.core.utils.Navigator
import com.revolution.robotics.features.configure.delete.DeleteRobotDialog
import com.revolution.robotics.features.configure.robotPicture.RobotPictureDialog
import com.revolution.robotics.features.configure.save.SaveRobotDialog
import com.revolution.robotics.features.controllers.ControllerType

@Suppress("TooManyFunctions")
class ConfigurePresenter(
    private val configurationEventBus: ConfigurationEventBus,
    private val duplicateUserRobotInteractor: DuplicateUserRobotInteractor,
    private val deleteRobotInteractor: DeleteRobotInteractor,
    private val getUserConfigurationInteractor: GetUserConfigurationInteractor,
    private val dialogEventBus: DialogEventBus,
    private val userConfigurationStorage: UserConfigurationStorage,
    private val resourceResolver: ResourceResolver,
    private val navigator: Navigator
) : ConfigureMvp.Presenter,
    ConfigurationEventBus.Listener, DialogEventBus.Listener {

    override var model: ConfigureViewModel? = null
    override var view: ConfigureMvp.View? = null

    private var toolbarViewModel: ConfigureToolbarViewModel? = null

    var userRobot: UserRobot? = null

    private var selectedTab = ConfigurationTabs.CONNECTIONS
    private var selectedConfigId = -1

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
            getUserConfigurationInteractor.execute { config ->
                onConfigurationLoaded(config)
            }
        } else {
            onConfigurationLoaded(userConfigurationStorage.userConfiguration)
        }
    }

    private fun onConfigurationLoaded(config: UserConfiguration?) {
        setControllersTabName(config?.controller)
        userConfigurationStorage.userConfiguration = config
        config?.apply {
            if (selectedConfigId != config.id) {
                selectedTab = ConfigurationTabs.CONNECTIONS
                selectedConfigId = config.id
            }
            model?.setScreen(selectedTab)

            if (selectedTab == ConfigurationTabs.CONNECTIONS) {
                view?.showConnectionsScreen(selectedConfigId)
            } else if (config.controller != null) {
                view?.showControllerScreen(config.controller!!)
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
            userConfigurationStorage.setRobotName(
                event.extras.getString(SaveRobotDialog.KEY_NAME) ?: "",
                event.extras.getString(SaveRobotDialog.KEY_DESCRIPTION) ?: ""
            )
            toolbarViewModel?.title?.set(event.extras.getString(SaveRobotDialog.KEY_NAME) ?: "")
        } else if (event == DialogEvent.DEFAULT_CONTROLLER_CHANGED) {
            setControllersTabName(userConfigurationStorage.userConfiguration?.controller)
        }
    }

    private fun setControllersTabName(controller: Int?) {
        if (controller == null) {
            model?.controllerTabText?.value = R.string.configure_controller_tab_title_without_controller
        } else {
            model?.controllerTabText?.value = R.string.configure_controller_tab_title
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
        view?.showConnectionsScreen(selectedConfigId)
    }

    override fun onControllerTabSelected() {
        selectedTab = ConfigurationTabs.CONTROLLERS
        model?.setScreen(ConfigurationTabs.CONTROLLERS)
        if (userConfigurationStorage.userConfiguration?.controller != null) {
            view?.showControllerScreen(userConfigurationStorage.userConfiguration?.controller!!)
        }

    }

    override fun clearStorage() {
        selectedConfigId = -1
        userConfigurationStorage.userConfiguration = null
        userConfigurationStorage.controllerHolder = null
    }

    override fun onMotorConfigChangedEvent(event: MotorPort) {
        userConfigurationStorage.updateMotorPort(event)
        userConfigurationStorage.userConfiguration?.let {
            view?.updateConfig(it)
        }
        view?.hideDrawer()
    }

    override fun onSensorConfigChangedEvent(event: SensorPort) {
        userConfigurationStorage.updateSensorPort(event)
        userConfigurationStorage.userConfiguration?.let {
            view?.updateConfig(it)
        }
        view?.hideDrawer()
    }

    override fun onRobotImageClicked() {
        userRobot?.let { robot -> view?.showDialog(RobotPictureDialog.newInstance(robot.instanceId, robot.coverImage)) }
    }

    override fun onDeleteClicked() {
        userRobot?.let { view?.showDialog(DeleteRobotDialog.newInstance(it)) }
    }

    override fun onBackgroundProgramsClicked() {
        navigator.navigate(ConfigureFragmentDirections.toButtonlessProgramSelector())
    }

    override fun onPriorityClicked() {
        navigator.navigate(ConfigureFragmentDirections.toProgramPriority())
    }

    override fun deleteRobot() {
        userRobot?.let {
            deleteRobotInteractor.id = it.instanceId
            deleteRobotInteractor.configId = it.configurationId
            deleteRobotInteractor.execute()
        }
        navigator.back()
    }

    override fun onControllerTypeClicked() {
        userConfigurationStorage.changeControllerType {
            onControllerTabSelected()
        }
    }


    override fun onDuplicateClicked() {
        userRobot?.let {
            duplicateUserRobotInteractor.currentRobot = it
            duplicateUserRobotInteractor.execute {
                navigator.back()
            }
        }
    }

    override fun saveConfiguration() {
        userRobot?.let { robot ->
            view?.showSaveDialog(robot.name ?: "", robot.description ?: "")
        }
    }
}
