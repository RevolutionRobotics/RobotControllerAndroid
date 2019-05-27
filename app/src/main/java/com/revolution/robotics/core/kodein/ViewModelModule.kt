package com.revolution.robotics.core.kodein

import com.revolution.robotics.features.build.BuildRobotViewModel
import com.revolution.robotics.features.build.connect.availableRobotsFace.ConnectViewModel
import com.revolution.robotics.features.challenges.challengeDetail.ChallengeDetailViewModel
import com.revolution.robotics.features.challenges.challengeGroup.ChallengeGroupViewModel
import com.revolution.robotics.features.challenges.challengeList.ChallengeListViewModel
import com.revolution.robotics.features.coding.CodingViewModel
import com.revolution.robotics.features.configure.ConfigureViewModel
import com.revolution.robotics.features.configure.connections.ConfigureConnectionsViewModel
import com.revolution.robotics.features.configure.controller.program.priority.ProgramPriorityViewModel
import com.revolution.robotics.features.configure.controllers.ConfigureControllersViewModel
import com.revolution.robotics.features.configure.motor.MotorConfigurationViewModel
import com.revolution.robotics.features.configure.sensor.SensorConfigurationViewModel
import com.revolution.robotics.features.controllers.buttonless.ButtonlessProgramSelectorViewModel
import com.revolution.robotics.features.controllers.programSelector.ProgramSelectorViewModel
import com.revolution.robotics.features.controllers.setup.SetupViewModel
import com.revolution.robotics.features.controllers.typeSelector.TypeSelectorViewModel
import com.revolution.robotics.features.mainmenu.MainMenuViewModel
import com.revolution.robotics.features.mainmenu.settings.SettingsViewModel
import com.revolution.robotics.features.mainmenu.settings.about.AboutViewModel
import com.revolution.robotics.features.mainmenu.settings.firmware.FirmwareUpdateViewModel
import com.revolution.robotics.features.myRobots.MyRobotsViewModel
import com.revolution.robotics.features.play.PlayViewModel
import com.revolution.robotics.features.whoToBuild.WhoToBuildViewModel
import org.kodein.di.Kodein
import org.kodein.di.erased.bind
import org.kodein.di.erased.instance
import org.kodein.di.erased.provider

fun createViewModelModule() =
    Kodein.Module("ViewModelModule") {
        bind<MainMenuViewModel>() with provider { MainMenuViewModel(instance()) }
        bind<CodingViewModel>() with provider { CodingViewModel() }
        bind<ChallengeGroupViewModel>() with provider { ChallengeGroupViewModel() }
        bind<WhoToBuildViewModel>() with provider { WhoToBuildViewModel(instance()) }
        bind<MyRobotsViewModel>() with provider { MyRobotsViewModel(instance()) }
        bind<ConnectViewModel>() with provider { ConnectViewModel() }
        bind<BuildRobotViewModel>() with provider { BuildRobotViewModel(instance()) }
        bind<ConfigureViewModel>() with provider { ConfigureViewModel(instance()) }
        bind<ConfigureConnectionsViewModel>() with provider { ConfigureConnectionsViewModel() }
        bind<ConfigureControllersViewModel>() with provider { ConfigureControllersViewModel(instance()) }
        bind<MotorConfigurationViewModel>() with provider { MotorConfigurationViewModel(instance()) }
        bind<SensorConfigurationViewModel>() with provider { SensorConfigurationViewModel(instance()) }
        bind<SettingsViewModel>() with provider { SettingsViewModel(instance()) }
        bind<AboutViewModel>() with provider { AboutViewModel(instance()) }
        bind<FirmwareUpdateViewModel>() with provider { FirmwareUpdateViewModel(instance()) }
        bind<PlayViewModel>() with provider { PlayViewModel(instance()) }
        bind<TypeSelectorViewModel>() with provider { TypeSelectorViewModel(instance()) }
        bind<SetupViewModel>() with provider { SetupViewModel(instance()) }
        bind<ProgramSelectorViewModel>() with provider { ProgramSelectorViewModel(instance()) }
        bind<ProgramPriorityViewModel>() with provider { ProgramPriorityViewModel(instance()) }
        bind<ButtonlessProgramSelectorViewModel>() with provider { ButtonlessProgramSelectorViewModel(instance()) }
        bind<ChallengeListViewModel>() with provider { ChallengeListViewModel() }
        bind<ChallengeDetailViewModel>() with provider { ChallengeDetailViewModel() }
    }
