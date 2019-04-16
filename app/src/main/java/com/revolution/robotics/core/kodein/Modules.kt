package com.revolution.robotics.core.kodein

import android.content.Context
import androidx.room.Room
import com.revolution.robotics.blockly.utils.JavascriptResultHandler
import com.revolution.robotics.challenges.ChallengesViewModel
import com.revolution.robotics.coding.CodingViewModel
import com.revolution.robotics.core.db.RoboticsDatabase
import com.revolution.robotics.core.domain.local.UserConfigurationDao
import com.revolution.robotics.core.domain.local.UserRobotDao
import com.revolution.robotics.core.interactor.BuildStepInteractor
import com.revolution.robotics.core.interactor.ConfigurationInteractor
import com.revolution.robotics.core.interactor.RobotInteractor
import com.revolution.robotics.core.interactor.TestCodeInteractor
import com.revolution.robotics.core.kodein.utils.ResourceResolver
import com.revolution.robotics.core.navigation.Navigator
import com.revolution.robotics.core.utils.dynamicPermissions.DynamicPermissionHandler
import com.revolution.robotics.mainmenu.MainMenuViewModel
import com.revolution.robotics.robots.RobotsViewModel
import com.revolution.robotics.slider.BuildStepSliderViewModel
import org.kodein.di.Kodein
import org.kodein.di.erased.bind
import org.kodein.di.erased.instance
import org.kodein.di.erased.provider
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
    }

fun createInteractorModule() =
    Kodein.Module("InteractorModule") {
        bind<RobotInteractor>() with provider { RobotInteractor() }
        bind<BuildStepInteractor>() with provider { BuildStepInteractor() }
        bind<ConfigurationInteractor>() with provider { ConfigurationInteractor() }
        bind<TestCodeInteractor>() with provider { TestCodeInteractor() }
    }

fun createDbModule(context: Context) =
    Kodein.Module("DbModule") {
        bind<RoboticsDatabase>() with singleton {
            Room.databaseBuilder(
                context,
                RoboticsDatabase::class.java,
                "robotics-database"
            ).build()
        }
        bind<UserRobotDao>() with provider { instance<RoboticsDatabase>().userRobotDao() }
        bind<UserConfigurationDao>() with provider { instance<RoboticsDatabase>().userConfigurationDao() }
    }

fun createViewModelModule() =
    Kodein.Module("ViewModelModule") {
        bind<MainMenuViewModel>() with provider { MainMenuViewModel(instance()) }
        bind<CodingViewModel>() with provider { CodingViewModel() }
        bind<ChallengesViewModel>() with provider { ChallengesViewModel() }
        bind<RobotsViewModel>() with provider { RobotsViewModel() }
        bind<BuildStepSliderViewModel>() with provider { BuildStepSliderViewModel() }
    }
