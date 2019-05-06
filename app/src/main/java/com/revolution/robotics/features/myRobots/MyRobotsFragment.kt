package com.revolution.robotics.features.myRobots

import android.os.Bundle
import android.view.View
import androidx.viewpager.widget.ViewPager
import com.revolution.robotics.BaseFragment
import com.revolution.robotics.R
import com.revolution.robotics.core.extensions.forceApplyTransformer
import com.revolution.robotics.core.extensions.waitForLayout
import com.revolution.robotics.core.kodein.utils.ResourceResolver
import com.revolution.robotics.databinding.FragmentMyRobotsBinding
import com.revolution.robotics.features.myRobots.adapter.MyRobotsCarouselAdapter
import com.revolution.robotics.views.carousel.initCarouselPadding
import com.revolution.robotics.views.carousel.initCarouselVariables
import org.kodein.di.erased.instance

class MyRobotsFragment : BaseFragment<FragmentMyRobotsBinding, MyRobotsViewModel>(R.layout.fragment_my_robots),
    MyRobotsMvp.View, ViewPager.OnPageChangeListener {

    override val viewModelClass: Class<MyRobotsViewModel> = MyRobotsViewModel::class.java
    private val resourceResolver: ResourceResolver by kodein.instance()
    private val presenter: MyRobotsMvp.Presenter by kodein.instance()
    private val adapter = MyRobotsCarouselAdapter()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        presenter.register(this, viewModel)
        binding?.toolbarViewModel = MyRobotsToolbarViewModel(resourceResolver)
        binding?.myRobotsViewpager?.initCarouselVariables(this@MyRobotsFragment, adapter)
        view.waitForLayout {
            binding?.myRobotsViewpager?.initCarouselPadding(view.width)
        }
    }

    override fun onRobotsLoaded() {
        binding?.myRobotsViewpager?.forceApplyTransformer()
    }

    override fun showNextRobot() {
        binding?.apply { myRobotsViewpager.currentItem++ }
    }

    override fun showPreviousRobot() {
        binding?.apply { myRobotsViewpager.currentItem-- }
    }

    override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) = Unit

    override fun onPageScrollStateChanged(state: Int) = Unit

    override fun onPageSelected(position: Int) {
        presenter.onPageSelected(position)
    }
}
