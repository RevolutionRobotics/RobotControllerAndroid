package com.revolution.robotics.core.kodein

import com.revolution.robotics.features.challenges.ChallengesViewModel
import com.revolution.robotics.features.coding.CodingViewModel
import com.revolution.robotics.features.controller.LiveControllerViewModel
import com.revolution.robotics.features.mainmenu.MainMenuViewModel
import com.revolution.robotics.features.myRobots.MyRobotsViewModel
import com.revolution.robotics.features.robots.RobotsViewModel
import com.revolution.robotics.views.slider.BuildStepSliderViewModel
import org.kodein.di.Kodein
import org.kodein.di.erased.bind
import org.kodein.di.erased.instance
import org.kodein.di.erased.provider

fun createViewModelModule() =
    Kodein.Module("ViewModelModule") {
        bind<MainMenuViewModel>() with provider { MainMenuViewModel(instance()) }
        bind<CodingViewModel>() with provider { CodingViewModel() }
        bind<ChallengesViewModel>() with provider { ChallengesViewModel() }
        bind<RobotsViewModel>() with provider { RobotsViewModel(instance()) }
        bind<BuildStepSliderViewModel>() with provider { BuildStepSliderViewModel() }
        bind<LiveControllerViewModel>() with provider { LiveControllerViewModel(instance()) }
        bind<MyRobotsViewModel>() with provider { MyRobotsViewModel(instance()) }
    }
