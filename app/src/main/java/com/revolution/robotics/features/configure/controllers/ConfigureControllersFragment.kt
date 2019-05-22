package com.revolution.robotics.features.configure.controllers

import android.os.Bundle
import android.view.View
import androidx.viewpager.widget.ViewPager
import com.revolution.robotics.BaseFragment
import com.revolution.robotics.R
import com.revolution.robotics.core.extensions.waitForLayout
import com.revolution.robotics.databinding.FragmentConfigureControllersBinding
import com.revolution.robotics.features.configure.controller.ControllerInfoDialog
import com.revolution.robotics.features.configure.controllers.adapter.ControllersCarouselAdapter
import com.revolution.robotics.views.carousel.initCarouselPadding
import com.revolution.robotics.views.carousel.initCarouselVariables
import com.revolution.robotics.views.carousel.initTransformerWithDelay
import org.kodein.di.erased.instance

class ConfigureControllersFragment :
    BaseFragment<FragmentConfigureControllersBinding, ConfigureControllersViewModel>(
        R.layout.fragment_configure_controllers
    ),
    ConfigureControllersMvp.View, ViewPager.OnPageChangeListener {

    override val viewModelClass: Class<ConfigureControllersViewModel> = ConfigureControllersViewModel::class.java
    private val presenter: ConfigureControllersMvp.Presenter by kodein.instance()
    private val adapter = ControllersCarouselAdapter()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        presenter.register(this, viewModel)
        binding?.controllersViewpager?.initCarouselVariables(this@ConfigureControllersFragment, adapter)
        view.waitForLayout {
            binding?.controllersViewpager?.initCarouselPadding(view.width)
        }
    }

    override fun onDestroyView() {
        presenter.unregister()
        super.onDestroyView()
    }

    override fun onRobotsChanged() {
        adapter.notifyDataSetChanged()
        binding?.controllersViewpager?.initTransformerWithDelay()
    }

    override fun showNextRobot() {
        binding?.apply { controllersViewpager.currentItem++ }
    }

    override fun showPreviousRobot() {
        binding?.apply { controllersViewpager.currentItem-- }
    }

    override fun showInfoModal(dialog: ControllerInfoDialog) {
        dialog.show(fragmentManager)
    }

    override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) = Unit

    override fun onPageScrollStateChanged(state: Int) = Unit

    override fun onPageSelected(position: Int) {
        adapter.selectedPosition = position
        presenter.onPageSelected(position)
    }
}
