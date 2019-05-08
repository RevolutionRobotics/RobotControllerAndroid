package com.revolution.robotics.features.configure

class ConfigurePresenter : ConfigureMvp.Presenter {

    override var model: ConfigureViewModel? = null
    override var view: ConfigureMvp.View? = null

    override fun register(view: ConfigureMvp.View, model: ConfigureViewModel?) {
        super.register(view, model)
        onConnectionsSelected()
    }

    override fun onConnectionsSelected() {
        model?.setScreen(ConfigurationTabs.CONNECTIONS)
        view?.showConnectionsScreen()
    }

    override fun onControllerSelected() {
        model?.setScreen(ConfigurationTabs.CONTROLLERS)
        view?.showControllerScreen()
    }
}
