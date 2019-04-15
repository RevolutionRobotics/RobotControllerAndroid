package com.revolution.robotics.mainmenu

import com.revolution.robotics.BaseFragment
import com.revolution.robotics.R
import com.revolution.robotics.databinding.FragmentMainMenuBinding
import org.kodein.di.erased.instance

class MainMenuFragment : BaseFragment<FragmentMainMenuBinding, MainMenuViewModel>(R.layout.fragment_main_menu),
    MainMenuMvp.View {

    override val presenter: MainMenuMvp.Presenter by kodein.instance()

    override val viewModelClass: Class<MainMenuViewModel> = MainMenuViewModel::class.java
}
