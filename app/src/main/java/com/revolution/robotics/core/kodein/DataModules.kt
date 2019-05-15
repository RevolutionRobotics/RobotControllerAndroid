package com.revolution.robotics.core.kodein

import android.content.Context
import androidx.room.Room
import com.revolution.robotics.core.db.RoboticsDatabase
import com.revolution.robotics.core.domain.local.UserConfigurationDao
import com.revolution.robotics.core.domain.local.UserRobotDao
import com.revolution.robotics.core.interactor.DeleteRobotInteractor
import com.revolution.robotics.core.interactor.GetAllUserRobotsInteractor
import com.revolution.robotics.core.interactor.GetUserConfigurationInteractor
import com.revolution.robotics.core.interactor.GetUserRobotInteractor
import com.revolution.robotics.core.interactor.SaveUserRobotInteractor
import com.revolution.robotics.core.interactor.firebase.BuildStepInteractor
import com.revolution.robotics.core.interactor.firebase.ConfigurationInteractor
import com.revolution.robotics.core.interactor.firebase.RobotInteractor
import com.revolution.robotics.core.interactor.firebase.TestCodeInteractor
import com.revolution.robotics.features.build.BuildRobotMvp
import com.revolution.robotics.features.build.BuildRobotPresenter
import com.revolution.robotics.features.build.buildFinished.BuildFinishedMvp
import com.revolution.robotics.features.build.buildFinished.BuildFinishedPresenter
import com.revolution.robotics.features.build.chapterFinished.ChapterFinishedMvp
import com.revolution.robotics.features.build.chapterFinished.ChapterFinishedPresenter
import com.revolution.robotics.features.build.connect.availableRobotsFace.ConnectMvp
import com.revolution.robotics.features.build.connect.availableRobotsFace.ConnectPresenter
import com.revolution.robotics.features.configure.ConfigureMvp
import com.revolution.robotics.features.configure.ConfigurePresenter
import com.revolution.robotics.features.configure.connections.ConfigureConnectionsMvp
import com.revolution.robotics.features.configure.connections.ConfigureConnectionsPresenter
import com.revolution.robotics.features.configure.controllers.ConfigureControllersMvp
import com.revolution.robotics.features.configure.controllers.ConfigureControllersPresenter
import com.revolution.robotics.features.configure.motor.MotorConfigurationMvp
import com.revolution.robotics.features.configure.motor.MotorConfigurationPresenter
import com.revolution.robotics.features.configure.sensor.SensorConfigurationMvp
import com.revolution.robotics.features.configure.sensor.SensorConfigurationPresenter
import com.revolution.robotics.features.play.liveController.LiveControllerMvp
import com.revolution.robotics.features.play.liveController.LiveControllerPresenter
import com.revolution.robotics.features.mainmenu.MainMenuMvp
import com.revolution.robotics.features.mainmenu.MainMenuPresenter
import com.revolution.robotics.features.mainmenu.settings.SettingsMvp
import com.revolution.robotics.features.mainmenu.settings.SettingsPresenter
import com.revolution.robotics.features.mainmenu.settings.about.AboutMvp
import com.revolution.robotics.features.mainmenu.settings.about.AboutPresenter
import com.revolution.robotics.features.mainmenu.settings.firmware.FirmwareMvp
import com.revolution.robotics.features.mainmenu.settings.firmware.FirmwareUpdatePresenter
import com.revolution.robotics.features.mainmenu.settings.firmware.update.FirmwareUpdateDialogPresenter
import com.revolution.robotics.features.mainmenu.settings.firmware.update.FirmwareUpdateMvp
import com.revolution.robotics.features.myRobots.MyRobotsMvp
import com.revolution.robotics.features.myRobots.MyRobotsPresenter
import com.revolution.robotics.features.play.PlayMvp
import com.revolution.robotics.features.play.PlayPresenter
import com.revolution.robotics.features.whoToBuild.WhoToBuildMvp
import com.revolution.robotics.features.whoToBuild.WhoToBuildPresenter
import org.kodein.di.Kodein
import org.kodein.di.erased.bind
import org.kodein.di.erased.instance
import org.kodein.di.erased.provider
import org.kodein.di.erased.singleton

fun createInteractorModule() =
    Kodein.Module("InteractorModule") {
        bind<RobotInteractor>() with provider { RobotInteractor() }
        bind<BuildStepInteractor>() with provider { BuildStepInteractor() }
        bind<ConfigurationInteractor>() with provider { ConfigurationInteractor() }
        bind<TestCodeInteractor>() with provider { TestCodeInteractor() }
        bind<GetUserRobotInteractor>() with provider { GetUserRobotInteractor(instance()) }
        bind<SaveUserRobotInteractor>() with provider { SaveUserRobotInteractor(instance(), instance()) }
        bind<GetAllUserRobotsInteractor>() with provider { GetAllUserRobotsInteractor(instance()) }
        bind<DeleteRobotInteractor>() with provider { DeleteRobotInteractor(instance()) }
        bind<GetUserConfigurationInteractor>() with provider { GetUserConfigurationInteractor(instance()) }
    }

fun createPresenterModule() =
    Kodein.Module("PresenterModule") {
        bind<MainMenuMvp.Presenter>() with singleton { MainMenuPresenter(instance()) }
        bind<WhoToBuildMvp.Presenter>() with singleton { WhoToBuildPresenter(instance(), instance(), instance()) }
        bind<LiveControllerMvp.Presenter>() with singleton { LiveControllerPresenter(instance()) }
        bind<MyRobotsMvp.Presenter>() with singleton { MyRobotsPresenter(instance(), instance(), instance()) }
        bind<ChapterFinishedMvp.Presenter>() with singleton { ChapterFinishedPresenter(instance()) }
        bind<BuildRobotMvp.Presenter>() with singleton { BuildRobotPresenter(instance(), instance(), instance()) }
        bind<ConnectMvp.Presenter>() with singleton { ConnectPresenter(instance(), instance()) }
        bind<ConfigureMvp.Presenter>() with singleton {
            ConfigurePresenter(
                instance(),
                instance(),
                instance(),
                instance(),
                instance()
            )
        }
        bind<ConfigureConnectionsMvp.Presenter>() with singleton {
            ConfigureConnectionsPresenter(
                instance(),
                instance()
            )
        }
        bind<MotorConfigurationMvp.Presenter>() with singleton { MotorConfigurationPresenter(instance(), instance()) }
        bind<SensorConfigurationMvp.Presenter>() with singleton { SensorConfigurationPresenter(instance(), instance()) }
        bind<BuildFinishedMvp.Presenter>() with singleton { BuildFinishedPresenter(instance()) }
        bind<ConfigureControllersMvp.Presenter>() with singleton { ConfigureControllersPresenter() }
        bind<SettingsMvp.Presenter>() with singleton { SettingsPresenter(instance()) }
        bind<AboutMvp.Presenter>() with singleton { AboutPresenter(instance(), instance()) }
        bind<FirmwareMvp.Presenter>() with singleton { FirmwareUpdatePresenter(instance()) }
        bind<PlayMvp.Presenter>() with singleton { PlayPresenter() }
        bind<FirmwareUpdateMvp.Presenter>() with singleton { FirmwareUpdateDialogPresenter(instance(), instance()) }
    }

fun createDbModule(context: Context) =
    Kodein.Module("DbModule") {
        bind<RoboticsDatabase>() with singleton {
            Room.databaseBuilder(context, RoboticsDatabase::class.java, "robotics-database")
                .fallbackToDestructiveMigration()
                .build()
        }
        bind<UserRobotDao>() with provider { instance<RoboticsDatabase>().userRobotDao() }
        bind<UserConfigurationDao>() with provider { instance<RoboticsDatabase>().userConfigurationDao() }
    }
