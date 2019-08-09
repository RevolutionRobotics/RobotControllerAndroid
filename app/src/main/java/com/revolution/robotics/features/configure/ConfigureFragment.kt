package com.revolution.robotics.features.configure

import android.os.Bundle
import android.view.View
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import com.revolution.robotics.BaseFragment
import com.revolution.robotics.R
import com.revolution.robotics.core.domain.local.UserConfiguration
import com.revolution.robotics.core.domain.local.UserRobot
import com.revolution.robotics.core.domain.remote.Motor
import com.revolution.robotics.core.domain.remote.Sensor
import com.revolution.robotics.core.eventBus.dialog.DialogEvent
import com.revolution.robotics.core.eventBus.dialog.DialogEventBus
import com.revolution.robotics.core.utils.BundleArgumentDelegate
import com.revolution.robotics.core.utils.CameraHelper
import com.revolution.robotics.databinding.FragmentConfigureBinding
import com.revolution.robotics.features.configure.connections.ConfigureConnectionsFragment
import com.revolution.robotics.features.configure.save.SaveRobotDialog
import com.revolution.robotics.features.controllers.setup.ConfigureControllerFragment
import org.kodein.di.erased.instance

// There are 3 Unit empty functions so this can be ignored
@Suppress("TooManyFunctions")
class ConfigureFragment : BaseFragment<FragmentConfigureBinding, ConfigureViewModel>(R.layout.fragment_configure),
    ConfigureMvp.View, DrawerLayout.DrawerListener, DialogEventBus.Listener {

    companion object {
        val Bundle.robotId: Int by BundleArgumentDelegate.Int("robotId")
    }

    override val viewModelClass: Class<ConfigureViewModel> = ConfigureViewModel::class.java
    private val presenter: ConfigureMvp.Presenter by kodein.instance()
    private val dialogEventBus: DialogEventBus by kodein.instance()
    private lateinit var cameraHelper: CameraHelper

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        presenter.register(this, viewModel)
        arguments?.robotId?.let { robotId ->
            binding?.toolbarViewModel = ConfigureToolbarViewModel(presenter).apply {
                presenter.loadRobot(robotId, this)
            }
            cameraHelper = CameraHelper(robotId)
        }

        binding?.drawerConfiguration?.addDrawerListener(this)
        dialogEventBus.register(this)
    }

    override fun hideDrawer() {
        binding?.drawerConfiguration?.closeDrawers()
    }

    override fun onBackPressed() =
        if (binding?.drawerConfiguration?.isDrawerOpen(GravityCompat.END) == true ||
            binding?.drawerConfiguration?.isDrawerOpen(GravityCompat.START) == true
        ) {
            hideDrawer()
            true
        } else {
            false
        }

    override fun onDestroyView() {
        presenter.unregister()
        binding?.drawerConfiguration?.removeDrawerListener(this)
        dialogEventBus.unregister(this)
        super.onDestroyView()
    }

    override fun openMotorConfig(motorPort: MotorPort) {
        binding?.drawerConfiguration?.setMotor(motorPort.motor ?: Motor(), motorPort.portName ?: "")
    }

    override fun openSensorConfig(sensorPort: SensorPort) {
        binding?.drawerConfiguration?.setSensor(sensorPort.sensor ?: Sensor(), sensorPort.portName ?: "")
    }

    override fun showConnectionsScreen(configId: Int) {
        commitFragmentToFrame(ConfigureConnectionsFragment.newInstance(configId))
    }

    override fun showControllerScreen(configId: Int) {
        commitFragmentToFrame(ConfigureControllerFragment.newInstance(configId))
    }

    private fun commitFragmentToFrame(fragment: Fragment) {
        fragmentManager?.beginTransaction()?.apply {
            replace(R.id.configureFragmentFrame, fragment).commitAllowingStateLoss()
        }
    }

    override fun updateConfig(userConfiguration: UserConfiguration) {
        (fragmentManager?.findFragmentById(R.id.configureFragmentFrame) as? ConfigureConnectionsFragment)?.apply {
            updateConfiguration(userConfiguration.id)
            clearSelection()
        }
    }

    override fun onDrawerClosed(drawerView: View) {
        (fragmentManager?.findFragmentById(R.id.configureFragmentFrame) as? ConfigureConnectionsFragment)?.apply {
            clearSelection()
        }
    }

    override fun onDialogEvent(event: DialogEvent) {
        when (event) {
            DialogEvent.DELETE_ROBOT -> presenter.deleteRobot()
            else -> Unit
        }
    }

    override fun showSaveDialog(name: String, description: String) {
        SaveRobotDialog.newInstance(name, description).show(fragmentManager)
    }

    override fun onDrawerSlide(drawerView: View, slideOffset: Float) = Unit
    override fun onDrawerStateChanged(newState: Int) = Unit
    override fun onDrawerOpened(drawerView: View) = Unit
}
