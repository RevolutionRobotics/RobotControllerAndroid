package com.revolution.robotics.features.configure.connections

import com.revolution.robotics.core.Mvp
import com.revolution.robotics.core.domain.local.UserConfiguration

interface ConfigureConnectionsMvp : Mvp {

    interface View : Mvp.View

    interface Presenter : Mvp.Presenter<View, ConfigureConnectionsViewModel> {
        fun loadConfiguration(robotId: Int)
        fun setConfiguration(userConfiguration: UserConfiguration)
        fun clearSelection()
        fun play()
    }
}
