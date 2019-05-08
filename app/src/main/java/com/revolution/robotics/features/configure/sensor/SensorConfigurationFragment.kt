package com.revolution.robotics.features.configure.sensor

import android.os.Bundle
import android.view.View
import com.revolution.robotics.BaseFragment
import com.revolution.robotics.R
import com.revolution.robotics.core.domain.remote.Sensor
import com.revolution.robotics.core.extensions.withArguments
import com.revolution.robotics.core.utils.BundleArgumentDelegate
import com.revolution.robotics.databinding.FragmentSensorConfigurationBinding
import org.kodein.di.erased.instance

class SensorConfigurationFragment :
    BaseFragment<FragmentSensorConfigurationBinding, SensorConfigurationViewModel>
        (R.layout.fragment_sensor_configuration), SensorConfigurationMvp.View {

    companion object {
        private var Bundle.sensor by BundleArgumentDelegate.Parcelable<Sensor>("sensor")
        private var Bundle.portName by BundleArgumentDelegate.String("portName")

        fun newInstance(sensor: Sensor, portName: String) = SensorConfigurationFragment().withArguments { bundle ->
            bundle.sensor = sensor
            bundle.portName = portName
        }
    }

    override val viewModelClass: Class<SensorConfigurationViewModel> = SensorConfigurationViewModel::class.java
    private val presenter: SensorConfigurationMvp.Presenter by kodein.instance()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presenter.register(this, viewModel)
        arguments?.let {
            presenter.setSensor(it.sensor, it.portName)
        }
    }

    override fun onDestroyView() {
        presenter.unregister()
        super.onDestroyView()
    }
}
