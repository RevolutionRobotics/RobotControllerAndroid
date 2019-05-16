package com.revolution.robotics

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.NavDirections
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import com.revolution.robotics.core.extensions.hideSystemUI
import com.revolution.robotics.core.utils.Navigator
import com.revolution.robotics.core.utils.dynamicPermissions.DynamicPermissionHandler
import com.revolution.robotics.features.bluetooth.BluetoothManager
import org.kodein.di.KodeinAware
import org.kodein.di.android.kodein
import org.kodein.di.erased.instance

class MainActivity : AppCompatActivity(), KodeinAware, Navigator.NavigationEventListener {

    override val kodein by kodein()
    private val dynamicPermissionHandler: DynamicPermissionHandler by instance()
    private val navigator: Navigator by instance()
    private val navController: NavController by lazy { findNavController(R.id.nav_host_fragment) }
    private val bluetoothManager: BluetoothManager by instance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        navigator.registerListener(this)
    }

    override fun onStart() {
        super.onStart()
        bluetoothManager.init(this)
    }

    override fun onResume() {
        super.onResume()
        window.hideSystemUI()
    }

    override fun onStop() {
        bluetoothManager.shutDown()
        super.onStop()
    }

    override fun onDestroy() {
        navigator.unregisterListener(this)
        super.onDestroy()
    }

    override fun onBackPressed() {
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment)
        val currentFragment = (navHostFragment as NavHostFragment).childFragmentManager.primaryNavigationFragment
        if ((currentFragment as? BaseFragment<*, *>)?.onBackPressed() != true) {
            super.onBackPressed()
        }
    }

    override fun onNavigationEvent(navDirections: NavDirections) {
        navController.navigate(navDirections)
    }

    override fun popUntil(fragmentId: Int) {
        while (navController.currentDestination?.id != fragmentId) {
            navController.popBackStack()
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) =
        dynamicPermissionHandler.onRequestPermissionsResult(requestCode, permissions, grantResults)
}
