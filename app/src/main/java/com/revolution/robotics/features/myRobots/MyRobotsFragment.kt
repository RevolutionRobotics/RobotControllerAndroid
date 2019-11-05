package com.revolution.robotics.features.myRobots

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewpager.widget.ViewPager
import com.revolution.robotics.BaseFragment
import com.revolution.robotics.R
import com.revolution.robotics.core.domain.local.UserRobot
import com.revolution.robotics.core.eventBus.dialog.DialogEvent
import com.revolution.robotics.core.eventBus.dialog.DialogEventBus
import com.revolution.robotics.core.extensions.waitForLayout
import com.revolution.robotics.core.kodein.utils.ResourceResolver
import com.revolution.robotics.core.utils.Navigator
import com.revolution.robotics.databinding.FragmentMyRobotsBinding
import com.revolution.robotics.features.myRobots.adapter.MyRobotsCarouselAdapter
import com.revolution.robotics.features.myRobots.info.InfoRobotDialog
import com.revolution.robotics.views.carousel.initCarouselPadding
import com.revolution.robotics.views.carousel.initCarouselVariables
import com.revolution.robotics.views.carousel.reInitTransformerWithDelay
import org.kodein.di.erased.instance

class MyRobotsFragment : BaseFragment<FragmentMyRobotsBinding, MyRobotsViewModel>(R.layout.fragment_my_robots),
    MyRobotsMvp.View, ViewPager.OnPageChangeListener, DialogEventBus.Listener {

    override val viewModelClass: Class<MyRobotsViewModel> = MyRobotsViewModel::class.java
    private val resourceResolver: ResourceResolver by kodein.instance()
    private val presenter: MyRobotsMvp.Presenter by kodein.instance()
    private val dialogEventBus: DialogEventBus by kodein.instance()
    private val navigator: Navigator by kodein.instance()

    private lateinit var adapter: MyRobotsCarouselAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = super.onCreateView(inflater, container, savedInstanceState)
        adapter = MyRobotsCarouselAdapter()
        return view
    }

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
        presenter.unregister()
        super.onDestroyView()
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.clearEmptyNavigationFlag()
    }

    override fun onRobotsChanged() {
        adapter.notifyDataSetChanged()
        binding?.myRobotsViewpager?.reInitTransformerWithDelay(viewModel?.currentPosition?.get() ?: 0)
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

    override fun onDialogEvent(event: DialogEvent) {
        when (event) {
            DialogEvent.DELETE_ROBOT -> handleDeleteRobotEvent(event)
            DialogEvent.EDIT_ROBOT -> handleEditRobot(event)
            DialogEvent.DUPLICATE_ROBOT -> handleDuplicateRobotEvent(event)
            DialogEvent.OPEN_BUILD_FLOW -> navigator.navigate(
                MyRobotsFragmentDirections.toBuildRobot(
                    event.extras.getParcelable(
                        InfoRobotDialog.KEY_ROBOT
                    )
                )
            )
            else -> Unit
        }
    }

    private fun handleDuplicateRobotEvent(event: DialogEvent) {
        event.extras.getParcelable<UserRobot>(InfoRobotDialog.KEY_ROBOT)?.let { robot ->
            binding?.myRobotsViewpager?.reInitTransformerWithDelay(adapter.selectedPosition + 1)
            presenter.duplicateRobot(robot)
        }
    }

    private fun handleEditRobot(event: DialogEvent) {
        event.extras.getParcelable<UserRobot>(InfoRobotDialog.KEY_ROBOT)?.let { robot ->
            if (adapter.selectedPosition == adapter.count - 1) {
                adapter.selectedPosition--
            }
            presenter.onEditSelected(robot)
        }
    }

    private fun handleDeleteRobotEvent(event: DialogEvent) {
        event.extras.getParcelable<UserRobot>(InfoRobotDialog.KEY_ROBOT)?.let { robotToDelete ->
            val selectedPosition = if (adapter.selectedPosition == adapter.count - 1) {
                adapter.selectedPosition - 1
            } else {
                adapter.selectedPosition
            }
            adapter.removeItems { it.id == robotToDelete.id }
            presenter.deleteRobot(robotToDelete, adapter.selectedPosition)
            binding?.myRobotsViewpager?.reInitTransformerWithDelay(selectedPosition)
        }
    }
}
