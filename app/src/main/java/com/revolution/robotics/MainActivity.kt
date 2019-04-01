package com.revolution.robotics

import androidx.appcompat.app.AppCompatActivity
import com.revolution.robotics.core.utils.dynamicPermissions.DynamicPermissionHandler
import org.koin.android.ext.android.inject

class MainActivity : AppCompatActivity() {

    private val dynamicPermissionHandler: DynamicPermissionHandler by inject()

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) =
        dynamicPermissionHandler.onRequestPermissionsResult(requestCode, permissions, grantResults)
}
