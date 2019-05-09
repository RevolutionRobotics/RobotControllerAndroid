package com.revolution.robotics.features.configure

import android.os.Bundle
import android.view.View
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import com.revolution.robotics.BaseFragment
import com.revolution.robotics.R
import com.revolution.robotics.core.domain.local.UserConfiguration
import com.revolution.robotics.core.domain.local.UserRobot
import com.revolution.robotics.core.domain.remote.Motor
import com.revolution.robotics.core.domain.remote.Sensor
import com.revolution.robotics.core.utils.BundleArgumentDelegate
import com.revolution.robotics.core.utils.dynamicPermissions.BluetoothConnectionFlowHelper
import com.revolution.robotics.databinding.FragmentConfigureBinding
import com.revolution.robotics.features.configure.connections.ConfigureConnectionsFragment
import com.revolution.robotics.features.configure.controllers.ConfigureControllersFragment
import org.kodein.di.erased.instance

@Suppress("UnnecessaryApply")
class ConfigureFragment : BaseFragment<FragmentConfigureBinding, ConfigureViewModel>(R.layout.fragment_configure),
    ConfigureMvp.View, BluetoothConnectionFlowHelper.Listener, DrawerLayout.DrawerListener {

    companion object {
        val Bundle.userRobot: UserRobot by BundleArgumentDelegate.Parcelable("userRobot")
    }

    override val viewModelClass: Class<ConfigureViewModel> = ConfigureViewModel::class.java
    private val presenter: ConfigureMvp.Presenter by kodein.instance()
    private val connectionFlowHelper = BluetoothConnectionFlowHelper(kodein)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        presenter.register(this, viewModel)
        arguments?.let {
            presenter.initRobot(it.userRobot)
            binding?.toolbarViewModel = ConfigureToolbarViewModel(it.userRobot.name ?: "", presenter)
        }

        connectionFlowHelper.init(fragmentManager, this)
        binding?.drawerConfiguration?.addDrawerListener(this)
    }

    override fun hideDrawer() {
        binding?.drawerConfiguration?.closeDrawers()
    }

    override fun onDestroyView() {
        presenter.unregister()
        connectionFlowHelper.shutdown()
        binding?.drawerConfiguration?.removeDrawerListener(this)
        super.onDestroyView()
    }

    override fun openMotorConfig(motorPort: MotorPort) {
        binding?.drawerConfiguration?.setMotor(motorPort.motor ?: Motor(), motorPort.portName ?: "")
    }

    override fun openSensorConfig(sensorPort: SensorPort) {
        binding?.drawerConfiguration?.setSensor(sensorPort.sensor ?: Sensor(), sensorPort.portName ?: "")
    }

    override fun startConnectionFlow() {
        connectionFlowHelper.startConnectionFlow(requireActivity())
    }

    override fun onBluetoothConnected() {
        viewModel?.isBluetoothConnected?.set(true)
    }

    override fun showConnectionsScreen(userConfiguration: UserConfiguration) {
        commitFragmentToFrame(ConfigureConnectionsFragment.newInstance(userConfiguration))
    }

    override fun showControllerScreen() {
        commitFragmentToFrame(ConfigureControllersFragment())
    }

    private fun commitFragmentToFrame(fragment: Fragment) {
        fragmentManager?.beginTransaction()?.apply {
            replace(R.id.configureFragmentFrame, fragment).commit()
        }
    }

    override fun updateConfig(userConfiguration: UserConfiguration) {
        (fragmentManager?.findFragmentById(R.id.configureFragmentFrame) as? ConfigureConnectionsFragment)?.apply {
            updateConfiguration(userConfiguration)
        }
    }

    override fun onDrawerClosed(drawerView: View) {
        (fragmentManager?.findFragmentById(R.id.configureFragmentFrame) as? ConfigureConnectionsFragment)?.apply {
            clearSelection()
        }
    }

    override fun onDrawerSlide(drawerView: View, slideOffset: Float) = Unit
    override fun onDrawerStateChanged(newState: Int) = Unit
    override fun onDrawerOpened(drawerView: View) = Unit
}
