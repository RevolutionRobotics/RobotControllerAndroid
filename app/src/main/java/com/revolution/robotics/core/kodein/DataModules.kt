package com.revolution.robotics.core.kodein

import android.content.Context
import androidx.room.Room
import com.revolution.robotics.features.build.chapterFinished.ChapterFinishedMvp
import com.revolution.robotics.features.build.chapterFinished.ChapterFinishedPresenter
import com.revolution.robotics.features.controller.LiveControllerMvp
import com.revolution.robotics.features.controller.LiveControllerPresenter
import com.revolution.robotics.core.db.RoboticsDatabase
import com.revolution.robotics.core.domain.local.UserConfigurationDao
import com.revolution.robotics.core.domain.local.UserRobotDao
import com.revolution.robotics.core.interactor.GetUserRobotInteractor
import com.revolution.robotics.core.interactor.SaveUserRobotInteractor
import com.revolution.robotics.core.interactor.firebase.BuildStepInteractor
import com.revolution.robotics.core.interactor.firebase.ConfigurationInteractor
import com.revolution.robotics.core.interactor.firebase.RobotInteractor
import com.revolution.robotics.core.interactor.firebase.TestCodeInteractor
import com.revolution.robotics.features.build.BuildRobotMvp
import com.revolution.robotics.features.build.BuildRobotPresenter
import com.revolution.robotics.features.build.connect.availableRobotsFace.ConnectMvp
import com.revolution.robotics.features.build.connect.availableRobotsFace.ConnectPresenter
import com.revolution.robotics.features.mainmenu.MainMenuMvp
import com.revolution.robotics.features.mainmenu.MainMenuPresenter
import com.revolution.robotics.features.myRobots.MyRobotsMvp
import com.revolution.robotics.features.myRobots.MyRobotsPresenter
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
        bind<SaveUserRobotInteractor>() with provider { SaveUserRobotInteractor(instance()) }
    }

fun createPresenterModule() =
    Kodein.Module("PresenterModule") {
        bind<MainMenuMvp.Presenter>() with singleton { MainMenuPresenter(instance()) }
        bind<WhoToBuildMvp.Presenter>() with singleton { WhoToBuildPresenter(instance(), instance()) }
        bind<LiveControllerMvp.Presenter>() with singleton { LiveControllerPresenter(instance()) }
        bind<MyRobotsMvp.Presenter>() with singleton { MyRobotsPresenter(instance()) }
        bind<ChapterFinishedMvp.Presenter>() with singleton { ChapterFinishedPresenter() }
        bind<BuildRobotMvp.Presenter>() with singleton { BuildRobotPresenter(instance(), instance(), instance()) }
        bind<ConnectMvp.Presenter>() with singleton { ConnectPresenter(instance()) }
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
