package com.revolution.robotics.features.configure.controllers

import android.os.Bundle
import android.view.View
import androidx.viewpager.widget.ViewPager
import com.revolution.robotics.BaseFragment
import com.revolution.robotics.R
import com.revolution.robotics.core.eventBus.dialog.DialogEvent
import com.revolution.robotics.core.eventBus.dialog.DialogEventBus
import com.revolution.robotics.core.extensions.waitForLayout
import com.revolution.robotics.databinding.FragmentConfigureControllersBinding
import com.revolution.robotics.features.configure.UserConfigurationStorage
import com.revolution.robotics.features.configure.controller.ControllerInfoDialog
import com.revolution.robotics.features.configure.controllers.adapter.ControllersCarouselAdapter
import com.revolution.robotics.features.controllers.delete.DeleteControllerDialog
import com.revolution.robotics.views.carousel.initCarouselPadding
import com.revolution.robotics.views.carousel.initCarouselVariables
import com.revolution.robotics.views.carousel.initTransformerWithDelay
import com.revolution.robotics.views.carousel.reInitTransformerWithDelay
import org.kodein.di.erased.instance

@Suppress("TooManyFunctions")
class ConfigureControllersFragment : BaseFragment<FragmentConfigureControllersBinding,
        ConfigureControllersViewModel>(R.layout.fragment_configure_controllers), ConfigureControllersMvp.View,
    ViewPager.OnPageChangeListener, DialogEventBus.Listener {

    override val viewModelClass: Class<ConfigureControllersViewModel> = ConfigureControllersViewModel::class.java
    private val presenter: ConfigureControllersMvp.Presenter by kodein.instance()
    private val dialogEventBus: DialogEventBus by kodein.instance()
    private val storage: UserConfigurationStorage by kodein.instance()
    private val adapter = ControllersCarouselAdapter()

    private var controllerDeleteId = -1

    companion object {
        fun newInstance() = ConfigureControllersFragment()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        presenter.register(this, viewModel)
        storage.robot?.let { presenter.loadControllers(it.instanceId) }
        dialogEventBus.register(this)
        binding?.controllersViewpager?.initCarouselVariables(this@ConfigureControllersFragment, adapter)
        view.waitForLayout {
            binding?.controllersViewpager?.initCarouselPadding(view.width)
        }
    }

    override fun onDestroyView() {
        presenter.unregister()
        dialogEventBus.unregister(this)
        super.onDestroyView()
    }

    override fun onControllersChanged() {
        adapter.notifyDataSetChanged()
        binding?.controllersViewpager?.initTransformerWithDelay()
    }

    override fun onDialogEvent(event: DialogEvent) {
        if (event == DialogEvent.DELETE_CONTROLLER) {
            val selectedPosition = if (adapter.selectedPosition == adapter.count - 1) {
                adapter.selectedPosition - 1
            } else {
                adapter.selectedPosition
            }
            adapter.removeItems { it.userController.id == controllerDeleteId }
            presenter.deleteController(controllerDeleteId, adapter.selectedPosition)
            binding?.controllersViewpager?.reInitTransformerWithDelay(selectedPosition)
            controllerDeleteId = -1
        }
    }

    override fun showDeleteControllerDialog(controllerId: Int) {
        controllerDeleteId = controllerId
        DeleteControllerDialog.newInstance().show(fragmentManager)
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
