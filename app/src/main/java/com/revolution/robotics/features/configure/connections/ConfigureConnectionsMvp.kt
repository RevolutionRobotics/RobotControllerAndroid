package com.revolution.robotics.features.configure.connections

import com.revolution.robotics.core.Mvp

interface ConfigureConnectionsMvp : Mvp {
    interface View : Mvp.View
    interface Presenter : Mvp.Presenter<View, ConfigureConnectionsViewModel>
}
