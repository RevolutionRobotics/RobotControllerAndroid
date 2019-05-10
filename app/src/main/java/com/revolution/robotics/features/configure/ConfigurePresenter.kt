package com.revolution.robotics.features.configure

import com.revolution.robotics.core.domain.local.UserConfiguration
import com.revolution.robotics.core.domain.local.UserMapping
import com.revolution.robotics.core.domain.local.UserRobot
import com.revolution.robotics.core.interactor.GetUserConfigurationInteractor

class ConfigurePresenter(
    private val configurationEventBus: ConfigurationEventBus,
    private val getUserConfigurationInteractor: GetUserConfigurationInteractor
) : ConfigureMvp.Presenter,
    ConfigurationEventBus.Listener {

    override var model: ConfigureViewModel? = null
    override var view: ConfigureMvp.View? = null

    var userConfiguration: UserConfiguration? = null
    var userRobot: UserRobot? = null

    override fun register(view: ConfigureMvp.View, model: ConfigureViewModel?) {
        super.register(view, model)
        configurationEventBus.register(this)
    }

    override fun initRobot(userRobot: UserRobot) {
        this.userRobot = userRobot
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
        super.unregister()
    }

    override fun onOpenMotorConfigEvent(event: MotorPort) {
        view?.openMotorConfig(event)
    }

    override fun onOpenSensorConfigEvent(event: SensorPort) {
        view?.openSensorConfig(event)
    }

    override fun onConnectionsTabSelected() {
        model?.setScreen(ConfigurationTabs.CONNECTIONS)
        userConfiguration?.let {
            view?.showConnectionsScreen(it)
        }
    }

    override fun onControllerTabSelected() {
        model?.setScreen(ConfigurationTabs.CONTROLLERS)
        view?.showControllerScreen()
    }

    override fun onMotorConfigChangedEvent(event: MotorPort) {
        userConfiguration?.let { config ->
            config.mappingId?.let { mapping ->
                setupMotorBasedOnName(event, mapping)
            }
            view?.updateConfig(config)
        }
        view?.hideDrawer()
    }

    override fun onSensorConfigChangedEvent(event: SensorPort) {
        userConfiguration?.let { config ->
            config.mappingId?.let { mapping ->
                setupSensorBasedOnName(event, mapping)
            }
            view?.updateConfig(config)
        }
        view?.hideDrawer()
    }

    private fun setupMotorBasedOnName(event: MotorPort, userMapping: UserMapping) {
        when (event.portName) {
            "M1" -> userMapping.M1 = event.motor
            "M2" -> userMapping.M2 = event.motor
            "M3" -> userMapping.M3 = event.motor
            "M4" -> userMapping.M4 = event.motor
            "M5" -> userMapping.M5 = event.motor
            "M6" -> userMapping.M6 = event.motor
        }
    }

    private fun setupSensorBasedOnName(event: SensorPort, userMapping: UserMapping) {
        when (event.portName) {
            "S1" -> userMapping.S1 = event.sensor
            "S2" -> userMapping.S2 = event.sensor
            "S3" -> userMapping.S3 = event.sensor
            "S4" -> userMapping.S4 = event.sensor
        }
    }

    override fun onRobotImageClicked() {
        userRobot?.id?.let {
            view?.showDialog(RobotPictureDialog.newInstance(it))
        }
    }

    override fun saveConfiguration() {
        // TODO save configuration
    }

    override fun onBluetoothClicked() {
        view?.startConnectionFlow()
    }
}
