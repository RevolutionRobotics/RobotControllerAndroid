package com.revolution.robotics.features.mainmenu

import android.os.Bundle
import android.view.View
import com.revolution.robotics.BaseFragment
import com.revolution.robotics.R
import com.revolution.robotics.databinding.FragmentMainMenuBinding
import com.revolution.robotics.features.onboarding.congratulations.CongratulationsDialog
import org.kodein.di.erased.instance

class MainMenuFragment :
    BaseFragment<FragmentMainMenuBinding, MainMenuViewModel>(R.layout.fragment_main_menu),
    MainMenuMvp.View {

    override val viewModelClass: Class<MainMenuViewModel> = MainMenuViewModel::class.java
    private val presenter: MainMenuMvp.Presenter by kodein.instance()


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding?.toolbarViewModel = MainMenuToolbarViewModel()
        presenter.register(this, viewModel)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        activity?.runOnUiThread { presenter.handleOnboarding() }
    }

    override fun showCongratulationsDialog() {
        CongratulationsDialog().show(fragmentManager)
    }

    override fun onDestroyView() {
        presenter.unregister()
        super.onDestroyView()
    }
}
