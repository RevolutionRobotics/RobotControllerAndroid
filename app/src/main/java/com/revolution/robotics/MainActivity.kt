package com.revolution.robotics

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.NavDirections
import androidx.navigation.findNavController
import com.revolution.robotics.core.navigation.NavigationEventBus
import com.revolution.robotics.core.utils.dynamicPermissions.DynamicPermissionHandler
import org.kodein.di.KodeinAware
import org.kodein.di.android.kodein
import org.kodein.di.erased.instance

class MainActivity : AppCompatActivity(), KodeinAware, NavigationEventBus.NavigationEventListener {

    override val kodein by kodein()
    private val dynamicPermissionHandler: DynamicPermissionHandler by instance()
    private val navigationEventBus: NavigationEventBus by instance()

    private val navController: NavController by lazy { findNavController(R.id.nav_host_fragment) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        navigationEventBus.registerListener(this)
    }

    override fun onDestroy() {
        super.onDestroy()
        navigationEventBus.unregisterListener(this)
    }

    override fun onNavigationEvent(navDirections: NavDirections) {
        navController.navigate(navDirections)
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) =
        dynamicPermissionHandler.onRequestPermissionsResult(requestCode, permissions, grantResults)
}
