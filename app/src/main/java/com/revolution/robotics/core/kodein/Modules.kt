package com.revolution.robotics.core.kodein

import android.content.Context
import com.revolution.robotics.core.interactor.BuildStepInteractor
import com.revolution.robotics.core.interactor.ConfigurationInteractor
import com.revolution.robotics.core.interactor.RobotInteractor
import com.revolution.robotics.core.interactor.TestCodeInteractor
import com.revolution.robotics.core.kodein.utils.ResourceResolver
import com.revolution.robotics.core.utils.dynamicPermissions.DynamicPermissionHandler
import com.revolution.robotics.mainmenu.MainMenuViewModel
import org.kodein.di.Kodein
import org.kodein.di.erased.bind
import org.kodein.di.erased.provider
import org.kodein.di.erased.singleton

fun createMainModule() =
    Kodein.Module("MainModule") {
        // Singletons
        bind<DynamicPermissionHandler>() with singleton { DynamicPermissionHandler() }
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

fun createViewModelModule() =
    Kodein.Module("ViewModelModule") {
        bind<MainMenuViewModel>() with provider { MainMenuViewModel() }
    }
