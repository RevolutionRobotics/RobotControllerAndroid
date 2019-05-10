package com.revolution.robotics.features.myRobots

import android.os.Bundle
import android.view.View
import androidx.viewpager.widget.ViewPager
import com.revolution.robotics.BaseFragment
import com.revolution.robotics.R
import com.revolution.robotics.core.eventBus.dialog.DialogEvent
import com.revolution.robotics.core.eventBus.dialog.DialogEventBus
import com.revolution.robotics.core.extensions.waitForLayout
import com.revolution.robotics.core.kodein.utils.ResourceResolver
import com.revolution.robotics.databinding.FragmentMyRobotsBinding
import com.revolution.robotics.features.myRobots.adapter.MyRobotsCarouselAdapter
import com.revolution.robotics.features.myRobots.delete.DeleteRobotDialog
import com.revolution.robotics.views.carousel.initCarouselPadding
import com.revolution.robotics.views.carousel.initCarouselVariables
import com.revolution.robotics.views.carousel.initTransformerWithDelay
import com.revolution.robotics.views.carousel.reInitTransformerWithDelay
import org.kodein.di.erased.instance

class MyRobotsFragment : BaseFragment<FragmentMyRobotsBinding, MyRobotsViewModel>(R.layout.fragment_my_robots),
    MyRobotsMvp.View, ViewPager.OnPageChangeListener, DialogEventBus.Listener {

    override val viewModelClass: Class<MyRobotsViewModel> = MyRobotsViewModel::class.java
    private val resourceResolver: ResourceResolver by kodein.instance()
    private val presenter: MyRobotsMvp.Presenter by kodein.instance()
    private val adapter = MyRobotsCarouselAdapter()
    private val dialogEventBus: DialogEventBus by kodein.instance()

    private var robotToDeleteId = -1

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        presenter.register(this, viewModel)
        binding?.toolbarViewModel = MyRobotsToolbarViewModel(resourceResolver)
        binding?.myRobotsViewpager?.initCarouselVariables(this@MyRobotsFragment, adapter)
        view.waitForLayout {
            binding?.myRobotsViewpager?.initCarouselPadding(view.width)
        }
        dialogEventBus.register(this)
    }

    override fun onDestroyView() {
        dialogEventBus.unregister(this)
        super.onDestroyView()
    }

    override fun onRobotsChanged() {
        adapter.notifyDataSetChanged()
        binding?.myRobotsViewpager?.initTransformerWithDelay()
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
        adapter.selectedPosition = position
        presenter.onPageSelected(position)
    }

    override fun deleteRobot(robotId: Int) {
        robotToDeleteId = robotId
        DeleteRobotDialog.newInstance().show(fragmentManager)
    }

    override fun onDialogEvent(event: DialogEvent) {
        if (event == DialogEvent.DELETE_ROBOT && robotToDeleteId != -1) {
            if (adapter.selectedPosition == adapter.count - 1) {
                adapter.selectedPosition--
            }
            binding?.myRobotsViewpager?.reInitTransformerWithDelay()
            presenter.deleteRobot(robotToDeleteId)
            adapter.removeItems { it.id == robotToDeleteId }
            robotToDeleteId = -1
        }
    }
}
