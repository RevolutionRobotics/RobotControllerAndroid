package com.revolution.robotics.core.kodein

import android.content.Context
import androidx.room.Room
import com.revolution.robotics.blockly.dialogs.colorPicker.ColorPickerMvp
import com.revolution.robotics.blockly.dialogs.colorPicker.ColorPickerPresenter
import com.revolution.robotics.blockly.dialogs.directionSelector.DirectionSelectorMvp
import com.revolution.robotics.blockly.dialogs.directionSelector.DirectionSelectorPresenter
import com.revolution.robotics.blockly.dialogs.donutSelector.DonutSelectorMvp
import com.revolution.robotics.blockly.dialogs.donutSelector.DonutSelectorPresenter
import com.revolution.robotics.blockly.dialogs.slider.SliderMvp
import com.revolution.robotics.blockly.dialogs.slider.SliderPresenter
import com.revolution.robotics.blockly.dialogs.soundPicker.SoundPickerMvp
import com.revolution.robotics.blockly.dialogs.soundPicker.SoundPickerPresenter
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
import com.revolution.robotics.core.interactor.UpdateUserRobotInteractor
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
import com.revolution.robotics.features.coding.CodingMvp
import com.revolution.robotics.features.coding.CodingPresenter
import com.revolution.robotics.features.coding.programs.ProgramsMvp
import com.revolution.robotics.features.coding.programs.ProgramsPresenter
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
import org.kodein.di.DKodeinAware
import org.kodein.di.Kodein
import org.kodein.di.bindings.NoArgBindingKodein
import org.kodein.di.bindings.NoArgSimpleBindingKodein
import org.kodein.di.bindings.Provider
import org.kodein.di.bindings.RefMaker
import org.kodein.di.bindings.Singleton
import org.kodein.di.erased
import org.kodein.di.erased.bind

fun createInteractorModule() =
    Kodein.Module("InteractorModule") {
        bind<RobotInteractor>() with p { RobotInteractor() }
        bind<BuildStepInteractor>() with p { BuildStepInteractor() }
        bind<ConfigurationInteractor>() with p { ConfigurationInteractor() }
        bind<TestCodeInteractor>() with p { TestCodeInteractor() }
        bind<GetUserRobotInteractor>() with p { GetUserRobotInteractor(i()) }
        bind<SaveNewUserRobotInteractor>() with p { SaveNewUserRobotInteractor(i(), i(), i(), i(), i()) }
        bind<UpdateUserRobotInteractor>() with p { UpdateUserRobotInteractor(i(), i()) }
        bind<GetAllUserRobotsInteractor>() with p { GetAllUserRobotsInteractor(i()) }
        bind<DeleteRobotInteractor>() with p { DeleteRobotInteractor(i(), i()) }
        bind<GetUserConfigurationInteractor>() with p { GetUserConfigurationInteractor(i()) }
        bind<ControllerInteractor>() with p { ControllerInteractor() }
        bind<ProgramInteractor>() with p { ProgramInteractor() }
        bind<ProgramsInteractor>() with p { ProgramsInteractor() }
        bind<ChallengeCategoriesInteractor>() with p { ChallengeCategoriesInteractor() }
        bind<GetUserControllersInteractor>() with p { GetUserControllersInteractor(i()) }
        bind<GetUserControllerInteractor>() with p { GetUserControllerInteractor(i(), i(), i()) }
        bind<RemoveUserControllerInteractor>() with p { RemoveUserControllerInteractor(i()) }
        bind<SaveUserControllerInteractor>() with p { SaveUserControllerInteractor(i(), i(), i(), i(), i()) }
        bind<SaveUserProgramInteractor>() with p { SaveUserProgramInteractor(i()) }
        bind<RemoveUserProgramInteractor>() with p { RemoveUserProgramInteractor(i()) }
        bind<GetUserChallengeCategoriesInteractor>() with p { GetUserChallengeCategoriesInteractor(i()) }
        bind<SaveUserChallengeCategoryInteractor>() with p { SaveUserChallengeCategoryInteractor(i()) }
        bind<GetUserProgramsInteractor>() with p { GetUserProgramsInteractor(i()) }
        bind<GetControllerTypeInteractor>() with p { GetControllerTypeInteractor(i(), i()) }
        bind<FirebaseInitInteractor>() with p { FirebaseInitInteractor() }
    }

@Suppress("LongMethod")
fun createPresenterModule() =
    Kodein.Module("PresenterModule") {
        bind<MainMenuMvp.Presenter>() with s { MainMenuPresenter(i(), i(), i()) }
        bind<WhoToBuildMvp.Presenter>() with s { WhoToBuildPresenter(i(), i(), i(), i()) }
        bind<MyRobotsMvp.Presenter>() with s { MyRobotsPresenter(i(), i(), i(), i()) }
        bind<ChapterFinishedMvp.Presenter>() with s { ChapterFinishedPresenter(i()) }
        bind<BuildRobotMvp.Presenter>() with s { BuildRobotPresenter(i(), i(), i(), i(), i(), i()) }
        bind<ConnectMvp.Presenter>() with s { ConnectPresenter(i(), i()) }
        bind<ConfigureMvp.Presenter>() with s { ConfigurePresenter(i(), i(), i(), i(), i(), i(), i(), i()) }
        bind<ConfigureConnectionsMvp.Presenter>() with s { ConfigureConnectionsPresenter(i(), i(), i()) }
        bind<MotorConfigurationMvp.Presenter>() with s { MotorConfigurationPresenter(i(), i(), i()) }
        bind<SensorConfigurationMvp.Presenter>() with s { SensorConfigurationPresenter(i(), i(), i()) }
        bind<BuildFinishedMvp.Presenter>() with s { BuildFinishedPresenter(i()) }
        bind<ConfigureControllersMvp.Presenter>() with s { ConfigureControllersPresenter(i(), i(), i(), i(), i()) }
        bind<SettingsMvp.Presenter>() with s { SettingsPresenter(i(), i()) }
        bind<AboutMvp.Presenter>() with s { AboutPresenter(i(), i()) }
        bind<FirmwareMvp.Presenter>() with s { FirmwareUpdatePresenter(i()) }
        bind<PlayMvp.Presenter>() with s { PlayPresenter() }
        bind<FirmwareUpdateMvp.Presenter>() with s { FirmwareUpdateDialogPresenter(i(), i()) }
        bind<TypeSelectorMvp.Presenter>() with s { TypeSelectorPresenter(i(), i()) }
        bind<SetupMvp.Presenter>() with s { SetupPresenter(i(), i(), i()) }
        bind<ProgramSelectorMvp.Presenter>() with s { ProgramSelectorPresenter(i(), i(), i(), i()) }
        bind<ProgramPriorityMvp.Presenter>() with s { ProgramPriorityPresenter(i(), i(), i(), i(), i()) }
        bind<ButtonlessProgramSelectorMvp.Presenter>() with s { ButtonlessProgramSelectorPresenter(i(), i(), i(), i()) }
        bind<SplashMvp.Presenter>() with s { SplashPresenter(i()) }
        bind<ChallengeGroupMvp.Presenter>() with s { ChallengeGroupPresenter(i(), i(), i()) }
        bind<ChallengeListMvp.Presenter>() with s { ChallengeListPresenter(i(), i()) }
        bind<ChallengeDetailMvp.Presenter>() with s { ChallengeDetailPresenter(i(), i(), i()) }
        bind<DirectionSelectorMvp.Presenter>() with s { DirectionSelectorPresenter() }
        bind<DonutSelectorMvp.Presenter>() with s { DonutSelectorPresenter() }
        bind<ColorPickerMvp.Presenter>() with s { ColorPickerPresenter() }
        bind<SoundPickerMvp.Presenter>() with s { SoundPickerPresenter(i()) }
        bind<SliderMvp.Presenter>() with s { SliderPresenter() }
        bind<CodingMvp.Presenter>() with s { CodingPresenter() }
        bind<ProgramsMvp.Presenter>() with s { ProgramsPresenter(i()) }
    }

fun createDbModule(context: Context) =
    Kodein.Module("DbModule") {
        bind<RoboticsDatabase>() with s {
            Room.databaseBuilder(context, RoboticsDatabase::class.java, "robotics-database")
                .fallbackToDestructiveMigration()
                .build()
        }
        bind<UserRobotDao>() with p { i<RoboticsDatabase>().userRobotDao() }
        bind<UserConfigurationDao>() with p { i<RoboticsDatabase>().userConfigurationDao() }
        bind<UserControllerDao>() with p { i<RoboticsDatabase>().userControllerDao() }
        bind<UserBackgroundProgramBindingDao>() with p { i<RoboticsDatabase>().userBackgroundProgramBindingDao() }
        bind<UserProgramDao>() with p { i<RoboticsDatabase>().userProgramDao() }
        bind<UserChallengeCategoryDao>() with p { i<RoboticsDatabase>().userChallengeCategoryDao() }
    }

inline fun <reified T : Any> DKodeinAware.i(tag: Any? = null) = dkodein.Instance<T>(erased(), tag)
inline fun <C, reified T : Any> Kodein.BindBuilder.WithScope<C>.s(
    ref: RefMaker? = null,
    sync: Boolean = true,
    noinline creator: NoArgSimpleBindingKodein<C>.() -> T
) = Singleton(scope, contextType, erased(), ref, sync, creator)

inline fun <C, reified T : Any> Kodein.BindBuilder.WithContext<C>.p(noinline creator: NoArgBindingKodein<C>.() -> T) =
    Provider(contextType, erased(), creator)
