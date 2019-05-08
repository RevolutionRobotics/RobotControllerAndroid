package com.revolution.robotics.features.configure

import com.revolution.robotics.core.Mvp

interface ConfigureMvp : Mvp {
    interface View : Mvp.View {
        fun showConnectionsScreen()
        fun showControllerScreen()
    }

    interface Presenter : Mvp.Presenter<View, ConfigureViewModel> {
        fun onConnectionsSelected()
        fun onControllerSelected()
    }
}
