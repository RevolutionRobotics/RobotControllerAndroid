package com.revolution.robotics

import androidx.appcompat.app.AppCompatActivity
import com.revolution.robotics.core.kodein.kodein
import com.revolution.robotics.core.utils.dynamicPermissions.DynamicPermissionHandler
import org.kodein.di.erased.instance

class MainActivity : AppCompatActivity() {

    private val dynamicPermissionHandler: DynamicPermissionHandler by kodein.instance()

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) =
        dynamicPermissionHandler.onRequestPermissionsResult(requestCode, permissions, grantResults)
}
