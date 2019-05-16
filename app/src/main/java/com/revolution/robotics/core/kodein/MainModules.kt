package com.revolution.robotics.core.kodein

import android.content.Context
import com.revolution.bluetooth.communication.RoboticsDeviceConnector
import com.revolution.robotics.blockly.utils.JavascriptResultHandler
import com.revolution.robotics.core.eventBus.dialog.DialogEventBus
import com.revolution.robotics.core.kodein.utils.ApplicationContextProvider
import com.revolution.robotics.core.kodein.utils.ResourceResolver
import com.revolution.robotics.core.utils.Navigator
import com.revolution.robotics.core.utils.dynamicPermissions.DynamicPermissionHandler
import com.revolution.robotics.features.bluetooth.BluetoothManager
import com.revolution.robotics.features.configure.ConfigurationEventBus
import com.revolution.robotics.features.configure.UserConfigurationStorage
import org.kodein.di.Kodein
import org.kodein.di.erased.bind
import org.kodein.di.erased.singleton

fun createMainModule() =
    Kodein.Module("MainModule") {
        // Singletons
        bind<DynamicPermissionHandler>() with singleton { DynamicPermissionHandler() }
        bind<Navigator>() with singleton { Navigator() }
        bind<JavascriptResultHandler>() with singleton { JavascriptResultHandler() }
        bind<DialogEventBus>() with singleton { DialogEventBus() }
        bind<ConfigurationEventBus>() with singleton { ConfigurationEventBus() }
        bind<BluetoothManager>() with singleton { BluetoothManager(kodein) }
        bind<RoboticsDeviceConnector>() with singleton { RoboticsDeviceConnector() }
        bind<UserConfigurationStorage>() with singleton { UserConfigurationStorage() }
    }

fun createAppModule(context: Context) =
    Kodein.Module("AppModule") {
        bind<ResourceResolver>() with singleton { ResourceResolver(context) }
        bind<ApplicationContextProvider>() with singleton { ApplicationContextProvider(context) }
    }
