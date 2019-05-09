package com.revolution.robotics.features.configure.connections

import com.revolution.robotics.core.Mvp
import com.revolution.robotics.core.domain.local.UserConfiguration

interface ConfigureConnectionsMvp : Mvp {
    interface View : Mvp.View
    interface Presenter : Mvp.Presenter<View, ConfigureConnectionsViewModel> {
        fun updateConfiguration(userConfiguration: UserConfiguration)
        fun clearSelection()
        fun setConfigurationId(id: Int)
    }
}
