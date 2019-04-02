package com.revolution.robotics.core.kodein

import com.revolution.robotics.core.utils.dynamicPermissions.DynamicPermissionHandler
import org.kodein.di.Kodein
import org.kodein.di.erased.bind
import org.kodein.di.erased.singleton

val appModule = Kodein.Module("AppModule") {

    // Singletons
    bind<DynamicPermissionHandler>() with singleton { DynamicPermissionHandler() }
}
