package com.revolution.robotics.features.mainmenu

import android.os.Bundle
import android.view.View
import com.revolution.robotics.BaseFragment
import com.revolution.robotics.R
import com.revolution.robotics.databinding.FragmentMainMenuBinding
import com.revolution.robotics.features.mainmenu.tutorial.TutorialViewModel
import org.kodein.di.erased.instance

class MainMenuFragment : BaseFragment<FragmentMainMenuBinding, MainMenuViewModel>(R.layout.fragment_main_menu),
    MainMenuMvp.View {

    override val viewModelClass: Class<MainMenuViewModel> = MainMenuViewModel::class.java
    private val presenter: MainMenuMvp.Presenter by kodein.instance()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        presenter.register(this, viewModel)
        binding?.toolbarViewModel = MainMenuToolbarViewModel()
        binding?.tutorialViewModel = TutorialViewModel()
    }

    override fun onDestroyView() {
        presenter.unregister()
        super.onDestroyView()
    }
}
