package com.revolution.robotics.features.configure

class ConfigurePresenter(private val configurationEventBus: ConfigurationEventBus) : ConfigureMvp.Presenter,
    ConfigurationEventBus.Listener {

    override var model: ConfigureViewModel? = null
    override var view: ConfigureMvp.View? = null

    override fun register(view: ConfigureMvp.View, model: ConfigureViewModel?) {
        super.register(view, model)
        configurationEventBus.register(this)
        onConnectionsTabSelected()
    }

    override fun unregister() {
        configurationEventBus.unregister(this)
        super.unregister()
    }

    override fun onConnectionsTabSelected() {
        model?.setScreen(ConfigurationTabs.CONNECTIONS)
        view?.showConnectionsScreen()
    }

    override fun onControllerTabSelected() {
        model?.setScreen(ConfigurationTabs.CONTROLLERS)
        view?.showControllerScreen()
    }

    override fun onMotorConfigChangedEvent(event: MotorUpdateEvent) {
        // TODO Deselect and update motor
        view?.hideDrawer()
    }

    override fun onSensorConfigChangedEvent(event: SensorUpdateEvent) {
        // TODO Deselect and update sensor
        view?.hideDrawer()
    }

    // TODO add robot ID here
    @Suppress("MagicNumber")
    override fun onRobotImageClicked() {
        view?.showDialog(RobotPictureDialog.newInstance(110))
    }

    override fun saveConfiguration() {
        // TODO save configuration
    }

    override fun onBluetoothClicked() {
        view?.startConnectionFlow()
    }
}
