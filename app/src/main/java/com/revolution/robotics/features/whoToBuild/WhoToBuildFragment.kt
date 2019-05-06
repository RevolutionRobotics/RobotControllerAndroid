package com.revolution.robotics.features.whoToBuild

import android.os.Bundle
import android.view.View
import androidx.viewpager.widget.ViewPager
import com.revolution.robotics.BaseFragment
import com.revolution.robotics.R
import com.revolution.robotics.core.extensions.forceApplyTransformer
import com.revolution.robotics.core.extensions.waitForLayout
import com.revolution.robotics.core.kodein.utils.ResourceResolver
import com.revolution.robotics.databinding.FragmentWhoToBuildBinding
import com.revolution.robotics.features.whoToBuild.adapter.RobotsCarouselAdapter
import com.revolution.robotics.views.carousel.initCarouselPadding
import com.revolution.robotics.views.carousel.initCarouselVariables
import org.kodein.di.erased.instance

@Suppress("UnnecessaryApply")
class WhoToBuildFragment : BaseFragment<FragmentWhoToBuildBinding, WhoToBuildViewModel>(R.layout.fragment_who_to_build),
    WhoToBuildMvp.View, ViewPager.OnPageChangeListener {

    override val viewModelClass: Class<WhoToBuildViewModel> = WhoToBuildViewModel::class.java
    private val adapter = RobotsCarouselAdapter()
    private val presenter: WhoToBuildMvp.Presenter by kodein.instance()
    private val resourceResolver: ResourceResolver by kodein.instance()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presenter.register(this, viewModel)
        binding?.toolbarViewModel = WhoToBuildToolbarViewModel(resourceResolver)
        binding?.robotsViewpager?.initCarouselVariables(this@WhoToBuildFragment, adapter)
        view.waitForLayout {
            binding?.robotsViewpager?.initCarouselPadding(view.width)
        }
    }

    override fun onDestroyView() {
        presenter.unregister()
        super.onDestroyView()
    }

    override fun onRobotsLoaded() {
        binding?.robotsViewpager?.forceApplyTransformer()
    }

    override fun showNextRobot() {
        binding?.apply { robotsViewpager.currentItem++ }
    }

    override fun showPreviousRobot() {
        binding?.apply { robotsViewpager.currentItem-- }
    }

    override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) = Unit

    override fun onPageScrollStateChanged(state: Int) = Unit

    override fun onPageSelected(position: Int) {
        presenter.onPageSelected(position)
    }
}
