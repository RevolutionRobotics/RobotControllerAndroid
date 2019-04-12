package com.revolution.robotics.core.utils.dynamicPermissions

import android.app.Activity
import android.content.pm.PackageManager
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat

class DynamicPermissionHandler {

    private var requestCode = 0
    private var requests = HashMap<Int, PermissionRequest>()

    fun permissions(vararg permissions: String) =
        PermissionRequest(this, permissions.toMutableList())

    fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        requests[requestCode]?.let { currentRequest ->
            val permissionsDenied = mutableListOf<String>()
            permissions.forEachIndexed { index, permission ->
                if (grantResults[index] != PackageManager.PERMISSION_GRANTED) {
                    permissionsDenied.add(permission)
                }
            }

            if (permissionsDenied.isEmpty()) {
                currentRequest.listener?.onAllPermissionsGranted()
            } else {
                currentRequest.listener?.onPermissionDenied(permissionsDenied.toList())
            }
        }
    }

    class PermissionRequest(
        private var handler: DynamicPermissionHandler?,
        private val permissions: MutableList<String>
    ) {

        var listener: DynamicPermissionListener? = null

        fun listener(listener: DynamicPermissionListener): PermissionRequest {
            this.listener = listener
            return this
        }

        fun request(activity: Activity) {
            val currentRequestCode = handler?.requestCode ?: 0
            handler?.let { handler ->
                handler.requests[currentRequestCode] = this
                handler.requestCode++
            }

            listener?.let { listener ->
                permissions.removeAll {
                    ContextCompat.checkSelfPermission(activity, it) == PackageManager.PERMISSION_GRANTED
                }

                if (permissions.isEmpty()) {
                    listener.onAllPermissionsGranted()
                } else {
                    ActivityCompat.requestPermissions(activity, permissions.toTypedArray(), currentRequestCode)
                }
            }
        }
    }
}
