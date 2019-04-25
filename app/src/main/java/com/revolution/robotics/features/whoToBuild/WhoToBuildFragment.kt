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
import com.revolution.robotics.features.whoToBuild.adapter.RobotsPageTransformer
import com.revolution.robotics.features.whoToBuild.adapter.RobotsPagerAdapter
import org.kodein.di.erased.instance
import kotlin.math.floor

@Suppress("UnnecessaryApply")
class WhoToBuildFragment : BaseFragment<FragmentWhoToBuildBinding, WhoToBuildViewModel>(R.layout.fragment_who_to_build),
    WhoToBuildMvp.View, ViewPager.OnPageChangeListener {

    companion object {
        private const val ROBOTS_PAGER_OFFSCREEN_PAGE_LIMIT = 3
        private const val VIEWPAGER_PADDING_MULTIPLIER = 0.3125
    }

    override val viewModelClass: Class<WhoToBuildViewModel> = WhoToBuildViewModel::class.java
    private val adapter = RobotsPagerAdapter()
    private val presenter: WhoToBuildMvp.Presenter by kodein.instance()
    private val resourceResolver: ResourceResolver by kodein.instance()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presenter.register(this, viewModel)
        binding?.apply {
            toolbarViewModel = WhoToBuildToolbarViewModel(resourceResolver)
            robotsViewpager.adapter = adapter
            robotsViewpager.offscreenPageLimit = ROBOTS_PAGER_OFFSCREEN_PAGE_LIMIT
            robotsViewpager.setPageTransformer(false, RobotsPageTransformer(robotsViewpager))
            robotsViewpager.addOnPageChangeListener(this@WhoToBuildFragment)
        }
        view.waitForLayout {
            val viewPagePadding = floor(view.width * VIEWPAGER_PADDING_MULTIPLIER).toInt()
            binding?.robotsViewpager?.setPaddingRelative(viewPagePadding, 0, viewPagePadding, 0)
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
