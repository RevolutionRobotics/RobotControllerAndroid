package com.revolution.robotics.core.kodein

import com.revolution.robotics.features.build.BuildRobotViewModel
import com.revolution.robotics.features.build.connect.availableRobotsFace.ConnectViewModel
import com.revolution.robotics.features.challenges.challengeDetail.ChallengeDetailViewModel
import com.revolution.robotics.features.challenges.challengeGroup.ChallengeGroupViewModel
import com.revolution.robotics.features.challenges.challengeList.ChallengeListViewModel
import com.revolution.robotics.features.coding.CodingViewModel
import com.revolution.robotics.features.coding.programs.ProgramsViewModel
import com.revolution.robotics.features.community.CommunityViewModel
import com.revolution.robotics.features.configure.ConfigureViewModel
import com.revolution.robotics.features.configure.connections.ConfigureConnectionsViewModel
import com.revolution.robotics.features.configure.controller.program.priority.ProgramPriorityViewModel
import com.revolution.robotics.features.configure.controllers.ConfigureControllersViewModel
import com.revolution.robotics.features.configure.motor.MotorConfigurationViewModel
import com.revolution.robotics.features.configure.sensor.SensorConfigurationViewModel
import com.revolution.robotics.features.controllers.buttonless.ButtonlessProgramSelectorViewModel
import com.revolution.robotics.features.controllers.programSelector.ProgramSelectorViewModel
import com.revolution.robotics.features.controllers.setup.ConfigureControllerViewModel
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

fun createViewModelModule() =
    Kodein.Module("ViewModelModule") {
        bind<MainMenuViewModel>() with p { MainMenuViewModel(i()) }
        bind<CodingViewModel>() with p { CodingViewModel(i(), i()) }
        bind<ChallengeGroupViewModel>() with p { ChallengeGroupViewModel() }
        bind<WhoToBuildViewModel>() with p { WhoToBuildViewModel(i()) }
        bind<MyRobotsViewModel>() with p { MyRobotsViewModel(i()) }
        bind<ConnectViewModel>() with p { ConnectViewModel() }
        bind<BuildRobotViewModel>() with p { BuildRobotViewModel(i()) }
        bind<ConfigureViewModel>() with p { ConfigureViewModel(i()) }
        bind<ConfigureConnectionsViewModel>() with p { ConfigureConnectionsViewModel() }
        bind<ConfigureControllersViewModel>() with p { ConfigureControllersViewModel(i()) }
        bind<MotorConfigurationViewModel>() with p { MotorConfigurationViewModel(i()) }
        bind<SensorConfigurationViewModel>() with p { SensorConfigurationViewModel(i()) }
        bind<SettingsViewModel>() with p { SettingsViewModel(i()) }
        bind<AboutViewModel>() with p { AboutViewModel(i()) }
        bind<FirmwareUpdateViewModel>() with p { FirmwareUpdateViewModel(i()) }
        bind<PlayViewModel>() with p { PlayViewModel(i()) }
        bind<TypeSelectorViewModel>() with p { TypeSelectorViewModel(i()) }
        bind<ConfigureControllerViewModel>() with p { ConfigureControllerViewModel(i()) }
        bind<ProgramSelectorViewModel>() with p { ProgramSelectorViewModel(i()) }
        bind<ProgramPriorityViewModel>() with p { ProgramPriorityViewModel() }
        bind<ButtonlessProgramSelectorViewModel>() with p { ButtonlessProgramSelectorViewModel(i()) }
        bind<ChallengeListViewModel>() with p { ChallengeListViewModel() }
        bind<ChallengeDetailViewModel>() with p { ChallengeDetailViewModel() }
        bind<ProgramsViewModel>() with p { ProgramsViewModel(i()) }
        bind<CommunityViewModel>() with p { CommunityViewModel(i()) }
    }
