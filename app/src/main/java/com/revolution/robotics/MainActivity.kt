package com.revolution.robotics

import androidx.appcompat.app.AppCompatActivity
import com.revolution.robotics.core.utils.dynamicPermissions.DynamicPermissionHandler
import org.kodein.di.KodeinAware
import org.kodein.di.android.kodein
import org.kodein.di.erased.instance

class MainActivity : AppCompatActivity(), KodeinAware {

    override val kodein by kodein()
    private val dynamicPermissionHandler: DynamicPermissionHandler by instance()

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) =
        dynamicPermissionHandler.onRequestPermissionsResult(requestCode, permissions, grantResults)
}
