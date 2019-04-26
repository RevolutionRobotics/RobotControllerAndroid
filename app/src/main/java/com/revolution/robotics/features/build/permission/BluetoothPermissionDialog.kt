package com.revolution.robotics.features.build.permission

import com.revolution.robotics.R
import com.revolution.robotics.core.eventBus.DialogEventBus
import com.revolution.robotics.core.utils.dynamicPermissions.DynamicPermissionHandler
import com.revolution.robotics.core.utils.dynamicPermissions.DynamicPermissionListener
import com.revolution.robotics.databinding.DialogBluetoothPermissionBinding
import com.revolution.robotics.features.build.BuildRobotFragment
import com.revolution.robotics.views.dialogs.DialogButton
import com.revolution.robotics.views.dialogs.DialogFace
import com.revolution.robotics.views.dialogs.RoboticsDialog
import org.kodein.di.erased.instance

class BluetoothPermissionDialog : RoboticsDialog(), DynamicPermissionListener {

    companion object {
        fun newInstance() = BluetoothPermissionDialog()
    }

    private val dynamicPermissionHandler: DynamicPermissionHandler by kodein.instance()
    private val dialogEventBus: DialogEventBus by kodein.instance()

    override val hasCloseButton = false
    override val dialogFaces: List<DialogFace<*>> = listOf(PermissionDialogFace())
    override val dialogButtons = listOf(
        DialogButton(R.string.permission_bluetooth_agree, R.drawable.ic_check, true) {
            dynamicPermissionHandler
                .permissions(BuildRobotFragment.REQUIRED_PERMISSIONS)
                .listener(this)
                .request(requireActivity())
        }
    )

    override fun onAllPermissionsGranted() {
        dialog.dismiss()
        dialogEventBus.publish(this, DialogEventBus.Event.POSITIVE)
    }

    override fun onPermissionDenied(deniedPermissions: List<String>) = Unit

    class PermissionDialogFace : DialogFace<DialogBluetoothPermissionBinding>(R.layout.dialog_bluetooth_permission)
}
