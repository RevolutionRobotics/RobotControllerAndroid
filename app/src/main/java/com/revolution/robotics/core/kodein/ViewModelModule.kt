package com.revolution.robotics.core.kodein

import com.revolution.robotics.features.build.connect.availableRobotsFace.ConnectViewModel
import com.revolution.robotics.features.build.BuildRobotViewModel
import com.revolution.robotics.features.challenges.ChallengesViewModel
import com.revolution.robotics.features.coding.CodingViewModel
import com.revolution.robotics.features.configure.ConfigureViewModel
import com.revolution.robotics.features.controller.LiveControllerViewModel
import com.revolution.robotics.features.mainmenu.MainMenuViewModel
import com.revolution.robotics.features.myRobots.MyRobotsViewModel
import com.revolution.robotics.features.configure.connections.ConfigureConnectionsViewModel
import com.revolution.robotics.features.whoToBuild.WhoToBuildViewModel
import org.kodein.di.Kodein
import org.kodein.di.erased.bind
import org.kodein.di.erased.instance
import org.kodein.di.erased.provider

fun createViewModelModule() =
    Kodein.Module("ViewModelModule") {
        bind<MainMenuViewModel>() with provider { MainMenuViewModel(instance()) }
        bind<CodingViewModel>() with provider { CodingViewModel() }
        bind<ChallengesViewModel>() with provider { ChallengesViewModel() }
        bind<WhoToBuildViewModel>() with provider { WhoToBuildViewModel(instance()) }
        bind<LiveControllerViewModel>() with provider { LiveControllerViewModel(instance()) }
        bind<MyRobotsViewModel>() with provider { MyRobotsViewModel(instance()) }
        bind<ConnectViewModel>() with provider { ConnectViewModel() }
        bind<BuildRobotViewModel>() with provider { BuildRobotViewModel(instance()) }
        bind<ConfigureViewModel>() with provider { ConfigureViewModel(instance()) }
        bind<ConfigureConnectionsViewModel>() with provider { ConfigureConnectionsViewModel() }
    }
