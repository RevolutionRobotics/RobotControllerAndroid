package com.revolution.robotics.core.utils.dynamicPermissions

import android.app.Activity
import android.content.pm.PackageManager
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat

class DynamicPermissionHandler {

    private val listeners = HashMap<Int, DynamicPermissionListener>()

    private var requestCode = 0

    fun request(permission: String, activity: Activity, onGrantedListener: () -> Unit) =
        request(permission, DynamicPermissionListener(onGrantedListener), activity)

    fun request(permission: String, listener: DynamicPermissionListener, activity: Activity) {
        val currentRequestCode = requestCode
        requestCode++
        listeners[currentRequestCode] = listener

        if (ContextCompat.checkSelfPermission(activity, permission) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(activity, arrayOf(permission), currentRequestCode)
        } else {
            listener.onPermissionGranted.invoke()
        }
    }

    fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        listeners[requestCode]?.let { listener ->
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                listener.onPermissionGranted.invoke()
            } else {
                listener.onPermissionDenied?.invoke()
            }
        }
    }

}