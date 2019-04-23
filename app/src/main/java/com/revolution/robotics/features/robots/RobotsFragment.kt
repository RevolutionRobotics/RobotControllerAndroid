package com.revolution.robotics.features.robots

import android.os.Bundle
import android.view.View
import androidx.viewpager.widget.ViewPager
import com.revolution.robotics.BaseFragment
import com.revolution.robotics.R
import com.revolution.robotics.core.extensions.waitForLayout
import com.revolution.robotics.databinding.FragmentRobotsBinding
import com.revolution.robotics.features.robots.adapter.RobotsPageTransformer
import com.revolution.robotics.features.robots.adapter.RobotsPagerAdapter
import org.kodein.di.erased.instance
import kotlin.math.floor

@Suppress("UnnecessaryApply")
class RobotsFragment : BaseFragment<FragmentRobotsBinding, RobotsViewModel>(R.layout.fragment_robots), RobotsMvp.View {

    companion object {
        private const val ROBOTS_PAGER_OFFSCREEN_PAGE_LIMIT = 3
        private const val VIEWPAGER_PADDING_MULTIPLIER = 0.3125
    }

    override val viewModelClass: Class<RobotsViewModel> = RobotsViewModel::class.java
    private lateinit var adapter: RobotsPagerAdapter
    private val presenter: RobotsMvp.Presenter by kodein.instance()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presenter.register(this, viewModel)
        binding?.apply {
            adapter = RobotsPagerAdapter(view.context)
            robotsViewpager.adapter = adapter
            robotsViewpager.offscreenPageLimit = ROBOTS_PAGER_OFFSCREEN_PAGE_LIMIT
            robotsViewpager.setPageTransformer(false, RobotsPageTransformer(robotsViewpager))
            robotsViewpager.addOnPageChangeListener(RobotsOnPageChangeListener())
        }
        view.waitForLayout {
            val viewPagePadding = floor(view.width * VIEWPAGER_PADDING_MULTIPLIER).toInt()
            binding?.robotsViewpager?.setPaddingRelative(viewPagePadding, 0, viewPagePadding, 0)
        }
    }

    override fun showNextRobot() {
        binding?.apply { robotsViewpager.currentItem++ }
    }

    override fun showPreviousRobot() {
        binding?.apply { robotsViewpager.currentItem-- }
    }

    inner class RobotsOnPageChangeListener : ViewPager.OnPageChangeListener {
        override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) = Unit
        override fun onPageScrollStateChanged(state: Int) = Unit

        override fun onPageSelected(position: Int) {
            presenter.onPageSelected(position)
        }
    }

    override fun onDestroyView() {
        presenter.unregister()
        super.onDestroyView()
    }
}
