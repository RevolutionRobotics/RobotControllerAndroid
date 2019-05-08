package com.revolution.robotics.features.configure

class ConfigurePresenter : ConfigureMvp.Presenter {

    override var model: ConfigureViewModel? = null
    override var view: ConfigureMvp.View? = null

    override fun register(view: ConfigureMvp.View, model: ConfigureViewModel?) {
        super.register(view, model)
        onConnectionsTabSelected()
    }

    override fun onConnectionsTabSelected() {
        model?.setScreen(ConfigurationTabs.CONNECTIONS)
        view?.showConnectionsScreen()
    }

    override fun onControllerTabSelected() {
        model?.setScreen(ConfigurationTabs.CONTROLLERS)
        view?.showControllerScreen()
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
