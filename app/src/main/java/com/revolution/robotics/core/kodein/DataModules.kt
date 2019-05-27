package com.revolution.robotics.core.kodein

import android.content.Context
import androidx.room.Room
import com.revolution.robotics.core.db.RoboticsDatabase
import com.revolution.robotics.core.domain.local.UserBackgroundProgramBindingDao
import com.revolution.robotics.core.domain.local.UserChallengeCategoryDao
import com.revolution.robotics.core.domain.local.UserConfigurationDao
import com.revolution.robotics.core.domain.local.UserControllerDao
import com.revolution.robotics.core.domain.local.UserProgramDao
import com.revolution.robotics.core.domain.local.UserRobotDao
import com.revolution.robotics.core.interactor.DeleteRobotInteractor
import com.revolution.robotics.core.interactor.FirebaseInitInteractor
import com.revolution.robotics.core.interactor.GetAllUserRobotsInteractor
import com.revolution.robotics.core.interactor.GetControllerTypeInteractor
import com.revolution.robotics.core.interactor.GetUserChallengeCategoriesInteractor
import com.revolution.robotics.core.interactor.GetUserConfigurationInteractor
import com.revolution.robotics.core.interactor.GetUserControllerInteractor
import com.revolution.robotics.core.interactor.GetUserControllersInteractor
import com.revolution.robotics.core.interactor.GetUserProgramsInteractor
import com.revolution.robotics.core.interactor.GetUserRobotInteractor
import com.revolution.robotics.core.interactor.RemoveUserControllerInteractor
import com.revolution.robotics.core.interactor.RemoveUserProgramInteractor
import com.revolution.robotics.core.interactor.SaveNewUserRobotInteractor
import com.revolution.robotics.core.interactor.SaveUserChallengeCategoryInteractor
import com.revolution.robotics.core.interactor.SaveUserControllerInteractor
import com.revolution.robotics.core.interactor.SaveUserProgramInteractor
import com.revolution.robotics.core.interactor.SaveUserRobotInteractor
import com.revolution.robotics.core.interactor.firebase.BuildStepInteractor
import com.revolution.robotics.core.interactor.firebase.ChallengeCategoriesInteractor
import com.revolution.robotics.core.interactor.firebase.ConfigurationInteractor
import com.revolution.robotics.core.interactor.firebase.ControllerInteractor
import com.revolution.robotics.core.interactor.firebase.ProgramInteractor
import com.revolution.robotics.core.interactor.firebase.ProgramsInteractor
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
import com.revolution.robotics.features.challenges.challengeDetail.ChallengeDetailMvp
import com.revolution.robotics.features.challenges.challengeDetail.ChallengeDetailPresenter
import com.revolution.robotics.features.challenges.challengeGroup.ChallengeGroupMvp
import com.revolution.robotics.features.challenges.challengeGroup.ChallengeGroupPresenter
import com.revolution.robotics.features.challenges.challengeList.ChallengeListMvp
import com.revolution.robotics.features.challenges.challengeList.ChallengeListPresenter
import com.revolution.robotics.features.configure.ConfigureMvp
import com.revolution.robotics.features.configure.ConfigurePresenter
import com.revolution.robotics.features.configure.connections.ConfigureConnectionsMvp
import com.revolution.robotics.features.configure.connections.ConfigureConnectionsPresenter
import com.revolution.robotics.features.configure.controller.program.priority.ProgramPriorityMvp
import com.revolution.robotics.features.configure.controller.program.priority.ProgramPriorityPresenter
import com.revolution.robotics.features.configure.controllers.ConfigureControllersMvp
import com.revolution.robotics.features.configure.controllers.ConfigureControllersPresenter
import com.revolution.robotics.features.configure.motor.MotorConfigurationMvp
import com.revolution.robotics.features.configure.motor.MotorConfigurationPresenter
import com.revolution.robotics.features.configure.sensor.SensorConfigurationMvp
import com.revolution.robotics.features.configure.sensor.SensorConfigurationPresenter
import com.revolution.robotics.features.controllers.buttonless.ButtonlessProgramSelectorMvp
import com.revolution.robotics.features.controllers.buttonless.ButtonlessProgramSelectorPresenter
import com.revolution.robotics.features.controllers.programSelector.ProgramSelectorMvp
import com.revolution.robotics.features.controllers.programSelector.ProgramSelectorPresenter
import com.revolution.robotics.features.controllers.setup.SetupMvp
import com.revolution.robotics.features.controllers.setup.SetupPresenter
import com.revolution.robotics.features.controllers.typeSelector.TypeSelectorMvp
import com.revolution.robotics.features.controllers.typeSelector.TypeSelectorPresenter
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
import com.revolution.robotics.features.splash.SplashMvp
import com.revolution.robotics.features.splash.SplashPresenter
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
        bind<SaveNewUserRobotInteractor>() with provider {
            SaveNewUserRobotInteractor(
                instance(),
                instance(),
                instance(),
                instance(),
                instance()
            )
        }
        bind<SaveUserRobotInteractor>() with provider { SaveUserRobotInteractor(instance(), instance()) }
        bind<GetAllUserRobotsInteractor>() with provider { GetAllUserRobotsInteractor(instance()) }
        bind<DeleteRobotInteractor>() with provider { DeleteRobotInteractor(instance()) }
        bind<GetUserConfigurationInteractor>() with provider { GetUserConfigurationInteractor(instance()) }
        bind<ControllerInteractor>() with provider { ControllerInteractor() }
        bind<ProgramInteractor>() with provider { ProgramInteractor() }
        bind<ProgramsInteractor>() with provider { ProgramsInteractor() }
        bind<ChallengeCategoriesInteractor>() with provider { ChallengeCategoriesInteractor() }
        bind<GetUserControllersInteractor>() with provider { GetUserControllersInteractor(instance()) }
        bind<GetUserControllerInteractor>() with provider {
            GetUserControllerInteractor(
                instance(),
                instance(),
                instance()
            )
        }
        bind<RemoveUserControllerInteractor>() with provider { RemoveUserControllerInteractor(instance()) }
        bind<SaveUserControllerInteractor>() with provider {
            SaveUserControllerInteractor(
                instance(),
                instance(),
                instance(),
                instance(),
                instance()
            )
        }
        bind<SaveUserProgramInteractor>() with provider { SaveUserProgramInteractor(instance()) }
        bind<RemoveUserProgramInteractor>() with provider { RemoveUserProgramInteractor(instance()) }
        bind<GetUserChallengeCategoriesInteractor>() with provider { GetUserChallengeCategoriesInteractor(instance()) }
        bind<SaveUserChallengeCategoryInteractor>() with provider { SaveUserChallengeCategoryInteractor(instance()) }
        bind<GetUserProgramsInteractor>() with provider { GetUserProgramsInteractor(instance()) }
        bind<GetControllerTypeInteractor>() with provider { GetControllerTypeInteractor(instance(), instance()) }
        bind<FirebaseInitInteractor>() with provider { FirebaseInitInteractor() }
    }

@Suppress("LongMethod")
fun createPresenterModule() =
    Kodein.Module("PresenterModule") {
        bind<MainMenuMvp.Presenter>() with singleton { MainMenuPresenter(instance()) }
        bind<WhoToBuildMvp.Presenter>() with singleton {
            WhoToBuildPresenter(
                instance(),
                instance(),
                instance(),
                instance()
            )
        }
        bind<MyRobotsMvp.Presenter>() with singleton {
            MyRobotsPresenter(
                instance(),
                instance(),
                instance(),
                instance()
            )
        }
        bind<ChapterFinishedMvp.Presenter>() with singleton { ChapterFinishedPresenter(instance()) }
        bind<BuildRobotMvp.Presenter>() with singleton {
            BuildRobotPresenter(
                instance(),
                instance(),
                instance(),
                instance(),
                instance(),
                instance()
            )
        }
        bind<ConnectMvp.Presenter>() with singleton { ConnectPresenter(instance(), instance()) }
        bind<ConfigureMvp.Presenter>() with singleton {
            ConfigurePresenter(
                instance(),
                instance(),
                instance(),
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
                instance(),
                instance()
            )
        }
        bind<MotorConfigurationMvp.Presenter>() with singleton {
            MotorConfigurationPresenter(
                instance(),
                instance(),
                instance()
            )
        }
        bind<SensorConfigurationMvp.Presenter>() with singleton {
            SensorConfigurationPresenter(
                instance(),
                instance(),
                instance()
            )
        }
        bind<BuildFinishedMvp.Presenter>() with singleton { BuildFinishedPresenter(instance()) }
        bind<ConfigureControllersMvp.Presenter>() with singleton {
            ConfigureControllersPresenter(
                instance(),
                instance(),
                instance(),
                instance()
            )
        }
        bind<SettingsMvp.Presenter>() with singleton { SettingsPresenter(instance()) }
        bind<AboutMvp.Presenter>() with singleton { AboutPresenter(instance(), instance()) }
        bind<FirmwareMvp.Presenter>() with singleton { FirmwareUpdatePresenter(instance()) }
        bind<PlayMvp.Presenter>() with singleton { PlayPresenter() }
        bind<FirmwareUpdateMvp.Presenter>() with singleton { FirmwareUpdateDialogPresenter(instance(), instance()) }
        bind<TypeSelectorMvp.Presenter>() with singleton { TypeSelectorPresenter(instance(), instance()) }
        bind<SetupMvp.Presenter>() with singleton { SetupPresenter(instance(), instance(), instance(), instance()) }
        bind<ProgramSelectorMvp.Presenter>() with singleton {
            ProgramSelectorPresenter(
                instance(),
                instance(),
                instance(),
                instance()
            )
        }
        bind<ProgramPriorityMvp.Presenter>() with singleton {
            ProgramPriorityPresenter(
                instance(),
                instance(),
                instance(),
                instance(),
                instance()
            )
        }
        bind<ButtonlessProgramSelectorMvp.Presenter>() with singleton {
            ButtonlessProgramSelectorPresenter(
                instance(),
                instance(),
                instance(),
                instance()
            )
        }
        bind<SplashMvp.Presenter>() with singleton { SplashPresenter(instance()) }
        bind<ChallengeGroupMvp.Presenter>() with singleton {
            ChallengeGroupPresenter(
                instance(),
                instance(),
                instance()
            )
        }
        bind<ChallengeListMvp.Presenter>() with singleton { ChallengeListPresenter(instance(), instance()) }
        bind<ChallengeDetailMvp.Presenter>() with singleton { ChallengeDetailPresenter() }
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
        bind<UserControllerDao>() with provider { instance<RoboticsDatabase>().userControllerDao() }
        bind<UserBackgroundProgramBindingDao>() with provider {
            instance<RoboticsDatabase>().userBackgroundProgramBindingDao()
        }
        bind<UserProgramDao>() with provider { instance<RoboticsDatabase>().userProgramDao() }
        bind<UserChallengeCategoryDao>() with provider { instance<RoboticsDatabase>().userChallengeCategoryDao() }
    }
