package com.revolution.robotics.core.koin

import com.revolution.robotics.core.utils.ConnectivityHandler
import com.revolution.robotics.core.utils.dynamicPermissions.DynamicPermissionHandler
import org.koin.dsl.module.module

val appModule = module {

    // Singletons
    single { DynamicPermissionHandler() }

    // Normal instances
    factory { ConnectivityHandler() }

}