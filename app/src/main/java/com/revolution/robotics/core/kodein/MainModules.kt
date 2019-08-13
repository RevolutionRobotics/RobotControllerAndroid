package com.revolution.robotics.core.kodein

import android.content.Context
import com.revolution.robotics.blockly.utils.BlocklyResultHolder
import com.revolution.robotics.core.eventBus.dialog.DialogEventBus
import com.revolution.robotics.core.interactor.firebase.FirebaseFileDownloader
import com.revolution.robotics.core.kodein.utils.ApplicationContextProvider
import com.revolution.robotics.core.kodein.utils.ResourceResolver
import com.revolution.robotics.core.utils.AppPrefs
import com.revolution.robotics.core.utils.Navigator
import com.revolution.robotics.core.utils.dynamicPermissions.DynamicPermissionHandler
import com.revolution.robotics.features.bluetooth.BluetoothManager
import com.revolution.robotics.features.configure.ConfigurationEventBus
import com.revolution.robotics.features.configure.controller.CompatibleProgramFilterer
import com.revolution.robotics.features.shared.ErrorHandler
import org.kodein.di.Kodein
import org.kodein.di.erased.bind
import org.revolutionrobotics.robotcontroller.bluetooth.communication.RoboticsDeviceConnector

fun createMainModule() =
    Kodein.Module("MainModule") {
        // Singletons
        bind<DynamicPermissionHandler>() with s { DynamicPermissionHandler() }
        bind<Navigator>() with s { Navigator() }
        bind<BlocklyResultHolder>() with s { BlocklyResultHolder() }
        bind<DialogEventBus>() with s { DialogEventBus() }
        bind<ConfigurationEventBus>() with s { ConfigurationEventBus() }
        bind<BluetoothManager>() with s { BluetoothManager(kodein) }
        bind<RoboticsDeviceConnector>() with s { RoboticsDeviceConnector() }
        bind<CompatibleProgramFilterer>() with p { CompatibleProgramFilterer() }
    }

fun createAppModule(context: Context) =
    Kodein.Module("AppModule") {
        bind<ResourceResolver>() with s { ResourceResolver(context) }
        bind<ApplicationContextProvider>() with s { ApplicationContextProvider(context) }
        bind<AppPrefs>() with s { AppPrefs(context) }
        bind<ErrorHandler>() with s { ErrorHandler() }
        bind<FirebaseFileDownloader>() with p { FirebaseFileDownloader(i(), i()) }
    }
