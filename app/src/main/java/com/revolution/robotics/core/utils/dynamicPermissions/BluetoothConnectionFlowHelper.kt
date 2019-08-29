package com.revolution.robotics.core.utils.dynamicPermissions

import android.Manifest
import android.app.Activity
import androidx.fragment.app.FragmentManager
import com.revolution.robotics.core.eventBus.dialog.DialogEvent
import com.revolution.robotics.core.eventBus.dialog.DialogEventBus
import com.revolution.robotics.features.build.connect.ConnectDialog
import com.revolution.robotics.features.build.connectionResult.ConnectionFailedDialog
import com.revolution.robotics.features.build.connectionResult.ConnectionSuccessDialog
import com.revolution.robotics.features.build.permission.BluetoothPermissionDialog
import com.revolution.robotics.features.build.turnOnTheBrain.TurnOnTheBrainDialog
import org.kodein.di.Kodein
import org.kodein.di.erased.instance

class BluetoothConnectionFlowHelper(kodein: Kodein) : DialogEventBus.Listener {

    companion object {
        val REQUIRED_PERMISSIONS = listOf(
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.ACCESS_FINE_LOCATION
        )
    }

    private val dynamicPermissionHandler: DynamicPermissionHandler by kodein.instance()
    private val dialogEventBus: DialogEventBus by kodein.instance()
    private var fragmentManager: FragmentManager? = null

    fun init(fragmentManager: FragmentManager?) {
        this.fragmentManager = fragmentManager
        dialogEventBus.register(this)
    }

    fun shutdown() {
        dialogEventBus.unregister(this)
        fragmentManager = null
    }

    fun startConnectionFlow(activity: Activity) {
        if (dynamicPermissionHandler.hasPermissions(REQUIRED_PERMISSIONS, activity)) {
            ConnectDialog.newInstance().show(fragmentManager)
        } else {
            BluetoothPermissionDialog.newInstance().show(fragmentManager)
        }
    }

    override fun onDialogEvent(event: DialogEvent) {
        when (event) {
            DialogEvent.PERMISSION_GRANTED -> ConnectDialog.newInstance().show(fragmentManager)
            DialogEvent.BRAIN_TURNED_ON -> ConnectDialog.newInstance().show(fragmentManager)
            DialogEvent.ROBOT_CONNECTED -> ConnectionSuccessDialog.newInstance().show(fragmentManager)
            DialogEvent.ROBOT_CONNECTION_FAILED -> ConnectionFailedDialog.newInstance().show(fragmentManager)
            DialogEvent.ROBOT_RECONNECT -> ConnectDialog.newInstance().show(fragmentManager)
            else -> Unit
        }
    }
}
