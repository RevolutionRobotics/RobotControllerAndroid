package com.revolution.robotics.features.mainmenu

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import com.revolution.robotics.BaseFragment
import com.revolution.robotics.R
import com.revolution.robotics.core.extensions.views
import com.revolution.robotics.databinding.FragmentMainMenuBinding
import com.revolution.robotics.databinding.LayoutTutorialBinding
import com.revolution.robotics.features.mainmenu.tutorial.TutorialViewModel
import org.kodein.di.erased.instance

class MainMenuFragment : BaseFragment<FragmentMainMenuBinding, MainMenuViewModel>(R.layout.fragment_main_menu),
    MainMenuMvp.View {

    override val viewModelClass: Class<MainMenuViewModel> = MainMenuViewModel::class.java
    private val presenter: MainMenuMvp.Presenter by kodein.instance()

    private var tutorialBinding: LayoutTutorialBinding? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding?.toolbarViewModel = MainMenuToolbarViewModel()
        presenter.register(this, viewModel)
    }

    override fun removeTutorialLayout() {
        binding?.constraintMainMenu?.views?.forEach { view ->
            if (view.tag == resources.getString(R.string.tag_tutorial)) {
                binding?.constraintMainMenu?.removeView(view)
            }
        }
    }

    override fun createTutorialLayout(tutorialViewModel: TutorialViewModel) {
        binding?.tutorialViewModel = tutorialViewModel
        tutorialBinding =
            LayoutTutorialBinding.inflate(LayoutInflater.from(context), binding?.constraintMainMenu, true)
        tutorialBinding?.tutorialViewModel = tutorialViewModel
    }

    override fun onDestroyView() {
        presenter.unregister()
        super.onDestroyView()
    }
}
