package com.revolution.robotics.robots

import android.os.Bundle
import android.view.View
import androidx.viewpager.widget.ViewPager

import com.revolution.robotics.BaseFragment
import com.revolution.robotics.R
import com.revolution.robotics.databinding.FragmentRobotsBinding
import com.revolution.robotics.robots.adapter.RobotsPageTransformer
import com.revolution.robotics.robots.adapter.RobotsPagerAdapter
import org.kodein.di.erased.instance

class RobotsFragment : BaseFragment<FragmentRobotsBinding, RobotsViewModel>(R.layout.fragment_robots), RobotsMvp.View {

    companion object {
        private const val ROBOTS_PAGER_OFFSCREEN_PAGE_LIMIT = 3
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
    }

    override fun showNextRobot() {
        binding?.apply { robotsViewpager.currentItem++ }
    }

    override fun showPreviousRobot() {
        binding?.apply { robotsViewpager.currentItem-- }
    }

    inner class RobotsOnPageChangeListener : ViewPager.OnPageChangeListener {
        override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {}
        override fun onPageScrollStateChanged(state: Int) {}

        override fun onPageSelected(position: Int) {
            presenter.onPageSelected(position)
        }
    }

    override fun onDestroyView() {
        presenter.unregister()
        super.onDestroyView()
    }
}
