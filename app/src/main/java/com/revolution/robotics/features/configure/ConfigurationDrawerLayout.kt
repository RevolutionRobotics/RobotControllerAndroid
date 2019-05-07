package com.revolution.robotics.features.configure

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.FragmentActivity
import com.revolution.robotics.R
import com.revolution.robotics.core.domain.remote.Motor
import com.revolution.robotics.core.domain.remote.Sensor
import com.revolution.robotics.features.configure.motor.MotorConfigurationFragment
import com.revolution.robotics.features.configure.sensor.SensorConfigurationFragment

class ConfigurationDrawerLayout @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null) :
    DrawerLayout(context, attrs), DrawerLayout.DrawerListener {

    init {
        setDrawerLockMode(LOCK_MODE_LOCKED_CLOSED)
        addDrawerListener(this)
    }

    fun setMotor(motor: Motor) {
        (context as FragmentActivity).supportFragmentManager.beginTransaction()
            .replace(R.id.container_motor_configuration, MotorConfigurationFragment.newInstance(motor, "M1"))
            .commitAllowingStateLoss()
        openDrawer(GravityCompat.END, true)
    }

    override fun onFinishInflate() {
        super.onFinishInflate()
        LayoutInflater.from(context).inflate(R.layout.drawer_sliding_layout, this)
    }

    fun setSensor(sensor: Sensor) {
        (context as FragmentActivity).supportFragmentManager.beginTransaction()
            .replace(R.id.container_sensor_configuration, SensorConfigurationFragment.newInstance(sensor, "S1"))
            .commitAllowingStateLoss()
        openDrawer(GravityCompat.START, true)
    }

    override fun onDrawerClosed(drawerView: View) {
        (context as FragmentActivity).supportFragmentManager.apply {
            findFragmentById(R.id.container_sensor_configuration)?.let { fragment ->
                beginTransaction().remove(fragment).commitAllowingStateLoss()
            }

            findFragmentById(R.id.container_motor_configuration)?.let { fragment ->
                beginTransaction().remove(fragment).commitAllowingStateLoss()
            }
        }
    }

    override fun onDrawerStateChanged(newState: Int) = Unit

    override fun onDrawerSlide(drawerView: View, slideOffset: Float) = Unit

    override fun onDrawerOpened(drawerView: View) = Unit
}