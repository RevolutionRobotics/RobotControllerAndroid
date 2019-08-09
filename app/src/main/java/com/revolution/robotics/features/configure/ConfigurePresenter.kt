package com.revolution.robotics.features.configure

import com.revolution.robotics.R
import com.revolution.robotics.core.domain.local.UserConfiguration
import com.revolution.robotics.core.domain.local.UserProgram
import com.revolution.robotics.core.domain.local.UserRobot
import com.revolution.robotics.core.eventBus.dialog.DialogEvent
import com.revolution.robotics.core.eventBus.dialog.DialogEventBus
import com.revolution.robotics.core.interactor.*
import com.revolution.robotics.core.kodein.utils.ResourceResolver
import com.revolution.robotics.core.utils.Navigator
import com.revolution.robotics.features.configure.controller.ControllerButton
import com.revolution.robotics.features.configure.delete.DeleteRobotDialog
import com.revolution.robotics.features.configure.robotPicture.RobotPictureDialog
import com.revolution.robotics.features.configure.save.SaveRobotDialog
import com.revolution.robotics.features.controllers.ControllerType

@Suppress("TooManyFunctions")
class ConfigurePresenter(
    private val getUserRobotInteractor: GetUserRobotInteractor,
    private val configurationEventBus: ConfigurationEventBus,
    private val duplicateUserRobotInteractor: DuplicateUserRobotInteractor,
    private val deleteRobotInteractor: DeleteRobotInteractor,
    private val updateUserRobotInteractor: UpdateUserRobotInteractor,
    private val getUserConfigurationInteractor: GetUserConfigurationInteractor,
    private val getUserControllerInteractor: GetUserControllerInteractor,
    private val saveUserControllerInteractor: SaveUserControllerInteractor,
    private val dialogEventBus: DialogEventBus,
    private val resourceResolver: ResourceResolver,
    private val navigator: Navigator
) : ConfigureMvp.Presenter,
    ConfigurationEventBus.Listener, DialogEventBus.Listener {

    override var model: ConfigureViewModel? = null
    override var view: ConfigureMvp.View? = null

    private var toolbarViewModel: ConfigureToolbarViewModel? = null

    var userRobot: UserRobot? = null
    var userConfiguration: UserConfiguration? = null

    private var selectedTab = ConfigurationTabs.CONNECTIONS

    override fun register(view: ConfigureMvp.View, model: ConfigureViewModel?) {
        super.register(view, model)
        configurationEventBus.register(this)
        dialogEventBus.register(this)
    }

    override fun loadRobot(robotId: Int, toolbarViewModel: ConfigureToolbarViewModel) {
        getUserRobotInteractor.robotId = robotId
        getUserRobotInteractor.execute { userRobot ->
            userRobot?.let {
                this.userRobot = it
                this.toolbarViewModel = toolbarViewModel
                toolbarViewModel.title.set(
                    if (it.name.isNullOrEmpty()) {
                        resourceResolver.string(R.string.untitled_robot_name)
                    } else {
                        it.name
                    }
                )

                getUserConfigurationInteractor.userConfigId = it.configurationId
                getUserConfigurationInteractor.execute { config ->
                    onConfigurationLoaded(config)
                }
            }
        }
    }

    private fun onConfigurationLoaded(config: UserConfiguration?) {
        setControllersTabName(config?.controller)
        config?.apply {
            if (config.id != userConfiguration?.id) {
                selectedTab = ConfigurationTabs.CONNECTIONS
                userConfiguration = config
            }
            model?.setScreen(selectedTab)

            if (selectedTab == ConfigurationTabs.CONNECTIONS) {
                view?.showConnectionsScreen(config.id)
            } else {
                view?.showControllerScreen(config.id)
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
            userRobot?.let {
                it.name = event.extras.getString(SaveRobotDialog.KEY_NAME) ?: ""
                it.description = event.extras.getString(SaveRobotDialog.KEY_DESCRIPTION) ?: ""
                save {
                    toolbarViewModel?.title?.set(event.extras.getString(SaveRobotDialog.KEY_NAME) ?: "")
                }
            }
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
        userConfiguration?.let { config ->
            selectedTab = ConfigurationTabs.CONNECTIONS
            model?.setScreen(ConfigurationTabs.CONNECTIONS)
            view?.showConnectionsScreen(config.id)
        }
    }

    override fun onControllerTabSelected() {
        selectedTab = ConfigurationTabs.CONTROLLERS
        model?.setScreen(ConfigurationTabs.CONTROLLERS)
        if (userConfiguration != null) {
            view?.showControllerScreen(userConfiguration!!.id)
        }
    }

    override fun onMotorConfigChangedEvent(event: MotorPort) {
        userConfiguration?.let { config ->
            config.mappingId?.updateMotorPort(event)
            save {
                view?.updateConfig(config)
                view?.hideDrawer()
            }
        }
    }

    override fun onSensorConfigChangedEvent(event: SensorPort) {
        userConfiguration?.let { config ->
            config.mappingId?.updateSensorPort(event)
            save {
                view?.updateConfig(config)
                view?.hideDrawer()
            }
        }
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
        userConfiguration?.controller?.let { navigator.navigate(ConfigureFragmentDirections.toProgramPriority(it)) }
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
        userConfiguration?.controller?.let { controllerId ->
            getUserControllerInteractor.id = controllerId
            getUserControllerInteractor.execute { controller ->
                controller.userController.type =
                    if (controller.userController.type == ControllerType.DRIVER.id) ControllerType.GAMER.id else ControllerType.DRIVER.id
                saveUserControllerInteractor.userController = controller.userController
                saveUserControllerInteractor.backgroundProgramBindings = controller.backgroundBindings
                saveUserControllerInteractor.execute {
                    onControllerTabSelected()
                }
            }
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

    override fun editRobotDetails() {
        userRobot?.let { robot ->
            view?.showSaveDialog(robot.name ?: "", robot.description ?: "")
        }
    }

    fun save(finished: (() -> Unit)? = null) {
        userConfiguration?.let { config ->
            userRobot?.let { robot ->
                updateUserRobotInteractor.userConfiguration = config
                updateUserRobotInteractor.userRobot = robot
                updateUserRobotInteractor.execute(onResponse = {
                    finished?.invoke()
                }, onError = { finished?.invoke() })
            }
        }
    }
}
