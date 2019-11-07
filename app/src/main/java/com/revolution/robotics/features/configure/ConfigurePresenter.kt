package com.revolution.robotics.features.configure

import com.revolution.robotics.R
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
import com.revolution.robotics.features.myRobots.MyRobotsFragmentDirections

@Suppress("TooManyFunctions")
class ConfigurePresenter(
    private val getUserRobotInteractor: GetUserRobotInteractor,
    private val configurationEventBus: ConfigurationEventBus,
    private val duplicateUserRobotInteractor: DuplicateUserRobotInteractor,
    private val deleteRobotInteractor: DeleteRobotInteractor,
    private val updateUserRobotInteractor: UpdateUserRobotInteractor,
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

                setControllersTabName(userRobot.configuration.controller)
                model?.setScreen(selectedTab)
                view?.showConnectionsScreen(userRobot.id)
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
        userRobot?.let { robot ->
            view?.openMotorConfig(robot.id, event)
        }
    }

    override fun onOpenSensorConfigEvent(event: SensorPort) {
        userRobot?.let { robot ->
            view?.openSensorConfig(robot.id, event)
        }
    }

    override fun onConnectionsTabSelected() {
        userRobot?.let { robot ->
            selectedTab = ConfigurationTabs.CONNECTIONS
            model?.setScreen(ConfigurationTabs.CONNECTIONS)
            view?.showConnectionsScreen(robot.id)
        }
    }

    override fun onControllerTabSelected() {
        selectedTab = ConfigurationTabs.CONTROLLERS
        model?.setScreen(ConfigurationTabs.CONTROLLERS)
        userRobot?.let { robot ->
            view?.showControllerScreen(robot.id)
        }
    }

    override fun onMotorConfigChangedEvent(event: MotorPort) {
        userRobot?.configuration?.let { config ->
            config.mappingId?.updateMotorPort(event)
            save {
                view?.updateConfig(config)
                view?.hideDrawer()
            }
        }
    }

    override fun onSensorConfigChangedEvent(event: SensorPort) {
        userRobot?.configuration?.let { config ->
            config.mappingId?.updateSensorPort(event)
            save {
                view?.updateConfig(config)
                view?.hideDrawer()
            }
        }
    }

    override fun onRobotImageClicked() {
        userRobot?.let { robot -> view?.showDialog(RobotPictureDialog.newInstance(robot.id, robot.coverImage)) }
    }

    override fun onDeleteClicked() {
        userRobot?.let { view?.showDialog(DeleteRobotDialog.newInstance(it)) }
    }

    override fun onBackgroundProgramsClicked() {
        userRobot?.let { navigator.navigate(ConfigureFragmentDirections.toButtonlessProgramSelector(it.id)) }
    }

    override fun onPriorityClicked() {
        userRobot?.configuration?.controller?.let { navigator.navigate(ConfigureFragmentDirections.toProgramPriority(it)) }
    }

    override fun deleteRobot() {
        userRobot?.let {
            deleteRobotInteractor.id = it.id
            deleteRobotInteractor.execute()
        }
        navigator.back()
    }

    override fun onControllerTypeClicked() {
        userRobot?.configuration?.controller?.let { controllerId ->
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

    override fun onAdvancedSettingsClicked() {
        view?.showAdvancedSettingsPopup()
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

    override fun onPlayEvent() {
        userRobot?.configuration?.controller?.let { controllerId ->
            getUserControllerInteractor.id = controllerId
            getUserControllerInteractor.execute { controller ->
                when (controller.userController.type) {
                    ControllerType.GAMER.id ->
                        navigator.navigate(MyRobotsFragmentDirections.toPlayGamer(userRobot?.id!!))
                    ControllerType.DRIVER.id ->
                        navigator.navigate(MyRobotsFragmentDirections.toPlayDriver(userRobot?.id!!))
                }
            }
        }
    }

    fun save(finished: (() -> Unit)? = null) {
        userRobot?.let { robot ->
            updateUserRobotInteractor.userRobot = robot
            updateUserRobotInteractor.execute(onResponse = {
                finished?.invoke()
            }, onError = { finished?.invoke() })
        }

    }
}
