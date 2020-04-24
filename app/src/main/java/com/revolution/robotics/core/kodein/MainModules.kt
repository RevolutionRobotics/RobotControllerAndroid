package com.revolution.robotics.core.kodein

import android.content.Context
import com.google.firebase.analytics.FirebaseAnalytics
import com.revolution.robotics.BuildConfig
import com.revolution.robotics.analytics.Reporter
import com.revolution.robotics.blockly.utils.BlocklyResultHolder
import com.revolution.robotics.core.api.RoboticsService
import com.revolution.robotics.core.cache.ImageCache
import com.revolution.robotics.core.eventBus.dialog.DialogEventBus
import com.revolution.robotics.core.interactor.api.ImageDownloader
import com.revolution.robotics.core.kodein.utils.ApplicationContextProvider
import com.revolution.robotics.core.kodein.utils.ResourceResolver
import com.revolution.robotics.core.utils.AppPrefs
import com.revolution.robotics.core.utils.CreateRobotInstanceHelper
import com.revolution.robotics.core.utils.FileManager
import com.revolution.robotics.core.utils.Navigator
import com.revolution.robotics.core.utils.dynamicPermissions.DynamicPermissionHandler
import com.revolution.robotics.features.bluetooth.BluetoothManager
import com.revolution.robotics.features.configure.ConfigurationEventBus
import com.revolution.robotics.features.configure.controller.CompatibleProgramFilterer
import com.revolution.robotics.features.shared.ErrorHandler
import okhttp3.Cache
import okhttp3.OkHttpClient
import org.kodein.di.Kodein
import org.kodein.di.erased.bind
import org.revolutionrobotics.bluetooth.android.communication.RoboticsDeviceConnector
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory

fun createMainModule() =
    Kodein.Module("MainModule") {
        // Singletons
        bind<DynamicPermissionHandler>() with s { DynamicPermissionHandler() }
        bind<Navigator>() with s { Navigator() }
        bind<BlocklyResultHolder>() with s { BlocklyResultHolder() }
        bind<DialogEventBus>() with s { DialogEventBus() }
        bind<ConfigurationEventBus>() with s { ConfigurationEventBus() }
        bind<BluetoothManager>() with s { BluetoothManager(kodein) }
        bind<CompatibleProgramFilterer>() with p { CompatibleProgramFilterer() }
        bind<CreateRobotInstanceHelper>() with p { CreateRobotInstanceHelper(i(), i(), i(), i()) }
        bind<RoboticsService>() with s {
            val retrofit: Retrofit = i()
            retrofit.create(RoboticsService::class.java)
        }
    }

fun createAppModule(context: Context) =
    Kodein.Module("AppModule") {
        bind<RoboticsDeviceConnector>() with s { RoboticsDeviceConnector(context) }
        bind<ResourceResolver>() with s { ResourceResolver(context) }
        bind<ApplicationContextProvider>() with s { ApplicationContextProvider(context) }
        bind<AppPrefs>() with s { AppPrefs(context) }
        bind<ErrorHandler>() with s { ErrorHandler() }
        bind<FirebaseAnalytics>() with s { FirebaseAnalytics.getInstance(context) }
        bind<Reporter>() with s { Reporter(i()) }
        bind<FileManager>() with s { FileManager(context) }
        bind<Retrofit>() with s {
            val CACHE_SIZE_BYTES: Long = 1024 * 1024 * 2
            val builder = OkHttpClient().newBuilder()
            builder.cache(
                Cache(context.cacheDir, CACHE_SIZE_BYTES)
            )
            val client = builder.build()
            val retrofitBuilder = Retrofit.Builder()
            retrofitBuilder
                .baseUrl(BuildConfig.BASE_URL)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
            retrofitBuilder.build()
        }
        bind<ImageCache>() with s { ImageCache(context) }
        bind<ImageDownloader>() with s { ImageDownloader(i(), context) }
    }
