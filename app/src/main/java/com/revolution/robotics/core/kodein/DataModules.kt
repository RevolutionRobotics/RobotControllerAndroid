package com.revolution.robotics.core.kodein

import android.content.Context
import androidx.room.Room
import com.revolution.robotics.features.milestoneFinished.MilestoneFinishedMvp
import com.revolution.robotics.features.milestoneFinished.MilestoneFinishedPresenter
import com.revolution.robotics.features.controller.LiveControllerMvp
import com.revolution.robotics.features.controller.LiveControllerPresenter
import com.revolution.robotics.core.db.RoboticsDatabase
import com.revolution.robotics.core.domain.local.UserConfigurationDao
import com.revolution.robotics.core.domain.local.UserRobotDao
import com.revolution.robotics.core.interactor.BuildStepInteractor
import com.revolution.robotics.core.interactor.ConfigurationInteractor
import com.revolution.robotics.core.interactor.RobotInteractor
import com.revolution.robotics.core.interactor.TestCodeInteractor
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
    }

fun createPresenterModule() =
    Kodein.Module("PresenterModule") {
        bind<MainMenuMvp.Presenter>() with singleton { MainMenuPresenter(instance()) }
        bind<WhoToBuildMvp.Presenter>() with singleton { WhoToBuildPresenter(instance()) }
        bind<LiveControllerMvp.Presenter>() with singleton { LiveControllerPresenter(instance()) }
        bind<MyRobotsMvp.Presenter>() with singleton { MyRobotsPresenter(instance()) }
        bind<MilestoneFinishedMvp.Presenter>() with singleton { MilestoneFinishedPresenter() }
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
