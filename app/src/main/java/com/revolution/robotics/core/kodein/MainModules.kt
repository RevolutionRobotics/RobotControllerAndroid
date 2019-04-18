package com.revolution.robotics.core.kodein

import android.content.Context
import com.revolution.robotics.blockly.utils.JavascriptResultHandler
import com.revolution.robotics.core.kodein.utils.ApplicationContextProvider
import com.revolution.robotics.core.kodein.utils.ResourceResolver
import com.revolution.robotics.core.navigation.Navigator
import com.revolution.robotics.core.utils.dynamicPermissions.DynamicPermissionHandler
import org.kodein.di.Kodein
import org.kodein.di.erased.bind
import org.kodein.di.erased.singleton

fun createMainModule() =
    Kodein.Module("MainModule") {
        // Singletons
        bind<DynamicPermissionHandler>() with singleton { DynamicPermissionHandler() }
        bind<Navigator>() with singleton { Navigator() }
        bind<JavascriptResultHandler>() with singleton { JavascriptResultHandler() }
    }

fun createAppModule(context: Context) =
    Kodein.Module("AppModule") {
        bind<ResourceResolver>() with singleton { ResourceResolver(context) }
        bind<ApplicationContextProvider>() with singleton { ApplicationContextProvider(context) }
    }
