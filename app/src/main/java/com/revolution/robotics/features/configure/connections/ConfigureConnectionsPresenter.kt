package com.revolution.robotics.features.configure.connections

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.lifecycle.MutableLiveData
import com.revolution.robotics.R
import com.revolution.robotics.core.domain.local.UserConfiguration
import com.revolution.robotics.core.domain.remote.Motor
import com.revolution.robotics.core.domain.remote.Sensor
import com.revolution.robotics.core.eventBus.dialog.DialogEvent
import com.revolution.robotics.core.eventBus.dialog.DialogEventBus
import com.revolution.robotics.core.interactor.GetUserConfigurationInteractor
import com.revolution.robotics.core.interactor.GetUserRobotInteractor
import com.revolution.robotics.core.kodein.utils.ResourceResolver
import com.revolution.robotics.features.configure.ConfigurationEventBus
import com.revolution.robotics.features.configure.MotorPort
import com.revolution.robotics.features.configure.SensorPort

@Suppress("TooManyFunctions")
class ConfigureConnectionsPresenter(
    private val getUserConfigurationInteractor: GetUserConfigurationInteractor,
    private val getUserRobotInteractor: GetUserRobotInteractor,
    private val openConfigurationEventBus: ConfigurationEventBus,
    private val resourceResolver: ResourceResolver,
    private val dialogEventBus: DialogEventBus
) :
    ConfigureConnectionsMvp.Presenter, DialogEventBus.Listener {

    override var model: ConfigureConnectionsViewModel? = null
    override var view: ConfigureConnectionsMvp.View? = null

    private var selectedPort: MutableLiveData<RobotPartModel>? = null
    private var userConfiguration: UserConfiguration? = null

    override fun register(view: ConfigureConnectionsMvp.View, model: ConfigureConnectionsViewModel?) {
        super.register(view, model)
        dialogEventBus.register(this)
    }

    override fun unregister(view: ConfigureConnectionsMvp.View?) {
        selectedPort = null
        dialogEventBus.unregister(this)
        super.unregister(view)
    }

    override fun onDialogEvent(event: DialogEvent) {
        if (event == DialogEvent.ROBOT_IMAGE_CHANGED) {
            model?.firebaseImageUrl?.value = model?.firebaseImageUrl?.value
            model?.robotId?.value = model?.robotId?.value
        }
    }

    override fun loadConfiguration(configurationId: Int) {
        getUserConfigurationInteractor.userConfigId = configurationId
        getUserConfigurationInteractor.execute { config ->
            userConfiguration = config?.apply {
                setConfiguration(this)
                getUserRobotInteractor.robotId
                getUserRobotInteractor.execute { robot ->
                    model?.firebaseImageUrl?.value = robot?.coverImage
                    model?.robotId?.value = robot?.instanceId
                }
            }
        }
    }

    override fun setConfiguration(userConfiguration: UserConfiguration) {
        this.userConfiguration = userConfiguration
        model?.apply {
            setupMotors(this)
            setupSensors(this)
        }
    }

    override fun clearSelection() {
        selectedPort = null
        userConfiguration?.let {
            setConfiguration(it)
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
        userConfiguration?.let { setConfiguration(it) }
    }

    private fun openMotorDrawer(motor: Motor?, portName: String?) {
        openConfigurationEventBus.publishOpenMotorConfiguration(MotorPort(motor, portName))
    }

    private fun openSensorDrawer(sensor: Sensor?, portName: String?) {
        openConfigurationEventBus.publishOpenSensorConfiguration(SensorPort(sensor, portName))
    }

    private fun getRobotPartModel(
        sensor: Sensor?,
        @StringRes portName: Int,
        port: MutableLiveData<RobotPartModel>
    ): RobotPartModel? =
        when {
            selectedPort == port -> RobotPartModel(portName, R.color.white, getSensorDrawable(sensor),
                isActive = true,
                isSelected = true
            ) {
                handlePortSelection(port)
                openSensorDrawer(sensor, resourceResolver.string(portName))
            }
            sensor == null || sensor.type.isNullOrEmpty() -> RobotPartModel(
                portName,
                R.color.grey_6d,
                getSensorDrawable(null),
                isActive = false,
                isSelected = false
            ) {
                handlePortSelection(port)
                openSensorDrawer(sensor, resourceResolver.string(portName))
            }
            else -> RobotPartModel(portName, R.color.golden_rod, getSensorDrawable(sensor),
                isActive = true,
                isSelected = false
            ) {
                handlePortSelection(port)
                openSensorDrawer(sensor, resourceResolver.string(portName))
            }
        }

    private fun getRobotPartModel(
        motor: Motor?,
        @StringRes portName: Int,
        port: MutableLiveData<RobotPartModel>
    ): RobotPartModel? =
        when {
            selectedPort == port -> RobotPartModel(portName, R.color.white, getMotorDrawable(motor),
                isActive = true,
                isSelected = true
            ) {
                handlePortSelection(port)
                openMotorDrawer(motor, resourceResolver.string(portName))
            }
            motor == null || motor.type.isNullOrEmpty() -> RobotPartModel(
                portName,
                R.color.grey_6d,
                getMotorDrawable(null),
                isActive = false,
                isSelected = false
            ) {
                handlePortSelection(port)
                openMotorDrawer(motor, resourceResolver.string(portName))
            }
            else -> RobotPartModel(portName, R.color.robotics_red, getMotorDrawable(motor),
                isActive = true,
                isSelected = false
            ) {
                handlePortSelection(port)
                openMotorDrawer(motor, resourceResolver.string(portName))
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
        Sensor.TYPE_ULTRASONIC -> R.drawable.ic_ultrasound
        Sensor.TYPE_BUMPER -> R.drawable.ic_bumper
        else -> R.drawable.ic_config_add
    }
}
