package com.revolution.robotics.features.configure.connections

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.lifecycle.MutableLiveData
import com.revolution.robotics.R
import com.revolution.robotics.core.domain.PortMapping
import com.revolution.robotics.core.domain.local.UserConfiguration
import com.revolution.robotics.core.domain.local.UserMapping
import com.revolution.robotics.core.domain.remote.Motor
import com.revolution.robotics.core.domain.remote.Sensor
import com.revolution.robotics.core.interactor.GetUserConfigurationInteractor

class ConfigureConnectionsPresenter(private val getUserConfigurationInteractor: GetUserConfigurationInteractor) :
    ConfigureConnectionsMvp.Presenter {
    override var model: ConfigureConnectionsViewModel? = null
    override var view: ConfigureConnectionsMvp.View? = null

    private var selectedPort: MutableLiveData<RobotPartModel>? = null
    private var userConfiguration: UserConfiguration? = null

    override fun setConfigurationId(id: Int) {
        getUserConfigurationInteractor.userConfigId = id
        getUserConfigurationInteractor.execute({ userConfig ->
            userConfiguration = userConfig
            updateConfiguration(userConfig)
        }, { error ->
            error.printStackTrace()
            userConfiguration = UserConfiguration(1, null, null).apply {
                mappingId = UserMapping(1, PortMapping().apply {
                    S1 = Sensor(1, Sensor.TYPE_ULTRASOUND, "sensor_1")
                })
            }
            userConfiguration?.let {
                updateConfiguration(it)
            }
        })
    }

    override fun updateConfiguration(userConfiguration: UserConfiguration) {
        model?.apply {
            setupMotors(this)
            setupSensors(this)
        }
    }

    private fun setupSensors(model: ConfigureConnectionsViewModel) {
        model.apply {
            partS1.value =
                getRobotPartModel(
                    userConfiguration?.mappingId?.S1,
                    R.string.configure_connections_button_S1,
                    partS1
                )
            partS2.value =
                getRobotPartModel(
                    userConfiguration?.mappingId?.S2,
                    R.string.configure_connections_button_S2,
                    partS2
                )
            partS3.value =
                getRobotPartModel(
                    userConfiguration?.mappingId?.S3,
                    R.string.configure_connections_button_S3,
                    partS3
                )
            partS4.value =
                getRobotPartModel(
                    userConfiguration?.mappingId?.S4,
                    R.string.configure_connections_button_S4,
                    partS4
                )
        }
    }

    private fun setupMotors(model: ConfigureConnectionsViewModel) {
        model.apply {
            partM1.value =
                getRobotPartModel(
                    userConfiguration?.mappingId?.M1,
                    R.string.configure_connections_button_M1,
                    partM1
                )
            partM2.value =
                getRobotPartModel(
                    userConfiguration?.mappingId?.M2,
                    R.string.configure_connections_button_M2,
                    partM2
                )
            partM3.value =
                getRobotPartModel(
                    userConfiguration?.mappingId?.M3,
                    R.string.configure_connections_button_M3,
                    partM3
                )
            partM4.value =
                getRobotPartModel(
                    userConfiguration?.mappingId?.M4,
                    R.string.configure_connections_button_M4,
                    partM4
                )
            partM5.value =
                getRobotPartModel(
                    userConfiguration?.mappingId?.M5,
                    R.string.configure_connections_button_M5,
                    partM5
                )
            partM6.value =
                getRobotPartModel(
                    userConfiguration?.mappingId?.M6,
                    R.string.configure_connections_button_M6,
                    partM6
                )
        }
    }

    private fun handlePortSelection(selectedPort: MutableLiveData<RobotPartModel>) {
        this.selectedPort = selectedPort
        userConfiguration?.let { updateConfiguration(it) }
    }

    private fun getRobotPartModel(
        sensor: Sensor?,
        @StringRes portName: Int,
        port: MutableLiveData<RobotPartModel>
    ): RobotPartModel? =
        when {
            selectedPort == port -> RobotPartModel(portName, R.color.golden_rod, getSensorDrawable(sensor), true) {
                handlePortSelection(port)
            }
            sensor == null -> RobotPartModel(portName, R.color.grey_6d, getSensorDrawable(null), false) {
                handlePortSelection(port)
            }
            else -> RobotPartModel(portName, R.color.robotics_red, getSensorDrawable(sensor), true) {
                handlePortSelection(port)
            }
        }

    private fun getRobotPartModel(
        motor: Motor?,
        @StringRes portName: Int,
        port: MutableLiveData<RobotPartModel>
    ): RobotPartModel? =
        when {
            selectedPort == port -> RobotPartModel(portName, R.color.golden_rod, getMotorDrawable(motor), true) {
                handlePortSelection(port)
            }
            motor == null -> RobotPartModel(portName, R.color.grey_6d, getMotorDrawable(null), false) {
                handlePortSelection(port)
            }
            else -> RobotPartModel(portName, R.color.robotics_red, getMotorDrawable(motor), true) {
                handlePortSelection(port)
            }
        }

    @DrawableRes
    private fun getMotorDrawable(motor: Motor?): Int = when (motor?.type) {
        Motor.TYPE_DRIVETRAIN -> R.drawable.ic_wheel
        Motor.TYPE_MOTOR -> R.drawable.ic_engine
        else -> R.drawable.ic_config_add
    }

    @DrawableRes
    private fun getSensorDrawable(sensor: Sensor?): Int = when (sensor?.type) {
        Sensor.TYPE_ULTRASOUND -> R.drawable.ic_ultrasound
        Sensor.TYPE_BUMPER -> R.drawable.ic_bumper
        else -> R.drawable.ic_config_add
    }
}
